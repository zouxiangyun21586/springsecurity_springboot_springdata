<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<style>
	#addPermissionDisplay,#permissionModificationDisplay {  
		display:none;  
		border:1em solid Thistle;
		height:30%;  
		width:20%;  
		position:absolute;/*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/  
		top:24%;/*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/  
		left:40%;  
		z-index:2;/*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/  
		background: plum;  
	}
	
	#addPermissionToHide,#permissionModificationHidden {  
		width: 100%;  
		height: 100%;  
		opacity:0.3;/*设置背景色透明度,1为完全不透明,IE需要使用filter:alpha(opacity=80);*/  
		filter:alpha(opacity=80);  
		display: none;  
		position:absolute;  
		top:0;  
		left:0;  
		z-index:1;  
		background: silver;  
	}
	
	a{ text-decoration:none;}
	/* css注释： 鼠标经过热点文字被加下划线 */ 
	a:hover{ text-decoration:underline;}
	/* a 标签字体颜色为 黑色 */
	a{color:#000}
	
	table {
		border-collapse:collapse;
		text-align:center; 
	}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-1.9.1.min.js"></script>
<body background="${pageContext.request.contextPath}/images/jianf.jpg">

	<center>
		<security:authorize permissionsUrl="/user/u" permissionsMethod="GET,All">
			<font size="3px"><a href="${pageContext.request.contextPath}/user/b"> 用户页面. </a></font>
			<br/>
		</security:authorize>
		<security:authorize permissionsUrl="/role/r" permissionsMethod="GET,All">
			<font size="3px"><a href="${pageContext.request.contextPath}/role/a"> 角色页面. </a></font>
			<br/>
		</security:authorize>
	</center>

	<div align="center" style="margin-top:59px">
		<table border="1">
			<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th>&nbsp;权限名&nbsp;</th>
				<th>&nbsp;权限Url&nbsp;</th>
				<th>&nbsp;权限请求&nbsp;</th>
				<security:authorize permissionsUrl="/power/p" permissionsMethod="PUT,DELETE,All">
					<th>&nbsp;操作&nbsp;</th>
				</security:authorize>
			</tr>
			<tbody id="link"></tbody>
		</table>
		<a href='javascript:add_show()' >添加权限</a>
		<br/><br/>
		
		<a href="javascript:void(0)" onclick="init(1)" id="sho">首页</a>
		<a href="javascript:void(0)" onclick="init(2)" id="shang">上页</a>
		<a href="javascript:void(0)" onclick="init(3)" id="xia">下页</a>
		<a href="javascript:void(0)" onclick="init(4)" id="wei">尾页</a> &nbsp;&nbsp;&nbsp;
		一页显示
		<select id="pageSizeId" onchange="init(5)">
			<option value="2">2</option>
			<option value="4">4</option>
			<option value="10">10</option>
			<option value="20">20</option>
		</select>条
		<br>
		总记录数：<span id="pageSizeCountId"></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		总页数：<span id="pageCountId"></span>       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		当前页：<span id="pageId"></span>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	
	<center>
		<a href='<c:url value="/logout" />'>点击此处退出登录.</a>
	</center>
	
	<form action="<%=request.getContextPath() %>/power/p" method="post">
		<div id="addPermissionDisplay">
			<div align="right">
				<button>
					<a href="javascript:add_hide()">&nbsp;X&nbsp;</a>
				</button>
			</div> 
			<div align="center">
				<p align="left">添加权限</p>
				权限名:<input name="powerName" id="aname" type="text"/><br/>
				权限url:<input name="httpUrl" id="aurl" type="text"/><br/>
				<input name="sub" type="submit" id="sub"/>
				<input name="res" type="reset" id="res"/>
			</div>
			</div>  
		<div id="addPermissionToHide"></div>
	</form>
	
	<div id="permissionModificationDisplay">
	<div align="right">
		<button>
			<a href="javascript:resource_hide()">&nbsp;X&nbsp;</a>
		</button>
	</div> 
	<div align="center">
		<form action="<%=request.getContextPath() %>/power/p" method="post">
			<p align="left">修改权限</p>
			<input type="hidden" name="id" id="rid">
			权限名:<input name="powerName" id="rname" type="text"/><br/>
			权限url:<input name="httpUrl" id="rurl" type="text"/><br/>
			权限请求:<input name="UsageMethod" id="rum" type="text"/><br/>
			<input name="sub" type="submit" id="sub"/>
			<input name="res" type="reset" id="res"/>
			<input type="hidden" name="_method" value="PUT" />
		</form>
	</div>
	</div>  
	<div id="permissionModificationHidden"></div>
	
	<form action="" method="POST" id="resourceDel">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
	
</body>
<script type="text/javascript">
	function init(mark) {
		var pageSize = "";
		var page = "";
		if(mark == 1)
		{
			pageSize = $("#pageSizeId").val();
			page = 1;
		}else if(mark == 2)
		{
			pageSize = $("#pageSizeId").val();
			var pageCount = $("#pageId").text();
			page = (parseInt(pageCount)-1);
			
		}else if(mark == 3)
		{
			pageSize = $("#pageSizeId").val();
			var pageCount = $("#pageId").text();
			page = (parseInt(pageCount)+1);
		}else if(mark == 4)
		{
			pageSize = $("#pageSizeId").val();
			page = $("#pageCountId").text();
		}else if(mark == 5)
		{
			pageSize = $("#pageSizeId").val();
			page = 1;
		}
		
		
		$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/power/p", // 发送请求的地址
			scriptCharset: 'utf-8',
			data:{pageSize:pageSize,page:page},
			dataType:"json",
			success:function(resource){
				$("#link").empty();
				
				var link = "";
				var list = resource.t;
				
				$("#pageSizeCountId").text(resource.pageSizeCount);
				$("#pageCountId").text(resource.pageCount);
				$("#pageId").text(resource.page);
				$("#pageSizeId").val(resource.pageSize);
				
				if(resource.page == 1){
					$("#shang").hide();
					$("#sho").hide();
				}else{
					$("#shang").show();
					$("#sho").show();
				}
				
				
				if(resource.page == resource.pageCount){
					$("#xia").hide();
					$("#wei").hide();
				}else{
					$("#xia").show();
					$("#wei").show();
				}
				
				for(var i in list){
					link +=  "<tr><td>"+list[i].id+"</td>"+
				 			"<td>"+list[i].powerName+"</td>"+
				 			"<td>"+list[i].httpUrl+"</td>"+
				 			"<td>"+list[i].usageMethod+"</td>"+
					 		"<td>"+
					 		"<security:authorize permissionsUrl='/power/p' permissionsMethod='PUT,All'>"+
					 		"<a href='javascript:resource_show("+list[i].id+")' >修改</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"<security:authorize permissionsUrl='/power/p' permissionsMethod='DELETE,All'>"+
					 		"<a href='javascript:void(0)' class='del' id='<%=request.getContextPath() %>/power/"+list[i].id+"'>删除</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"</td></tr>";
				}
				$("#link").append(link);
				loader();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
	        }
		});
	}
	
	/* 页面加载完出现 */
	$(document).ready(function(){
		init();
		loader();
	});
	
	
	function loader(){
		$(".del").click(function() {
			if(confirm("确认删除吗?")){
				var dlt = $(this).attr("id");
				$("#resourceDel").attr("action", dlt).submit();
				return false;
			}
		});
	}
	
	var ars = document.getElementById('addPermissionDisplay');
	var aro = document.getElementById('addPermissionToHide');
    function add_show()
    {  
    	ars.style.display = "block";
        aro.style.display = "block";
    }  
    function add_hide()
    {  
        ars.style.display = "none";
        aro.style.display = "none";
    }
    
    var rs = document.getElementById('permissionModificationDisplay');
	var ro = document.getElementById('permissionModificationHidden');
    function resource_show(id)
    {  
    	$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/power/getPower", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{"id":id},
			success:function(resource){
				$("#rid").val(resource.id);
				$("#rname").val(resource.powerName);
				$("#rurl").val(resource.httpUrl);
				$("#rum").val(resource.usageMethod);
			}
		})
        rs.style.display = "block";
        ro.style.display = "block";
    }  
    function resource_hide()
    {  
        rs.style.display = "none";
        ro.style.display = "none";
    }
    
</script>
</html>