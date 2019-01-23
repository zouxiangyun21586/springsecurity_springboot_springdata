<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
</head>
<style>

	#roleModificationDisplay,#roleWeightingDisplay,#rolePermissionWeightingDisplay {
		display:none;  
		border:1em solid AliceBlue;  
		height:45%;  
		width:20%;  
		position:absolute;/*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/  
		top:2%;/*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/  
		left:40%;  
		z-index:2;/*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/  
		background: Lavender;  
	} 
	
	#roleModificationHiding,#roleEmpowermentConcealment,#rolePermissionEmpowermentHiding {  
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
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-1.9.1.min.js"></script>
<body background="${pageContext.request.contextPath}/images/maozi.jpg">

	<center>
		<security:authorize permissionsUrl="/user/u" permissionsMethod="GET,All">
			<font size="3px"><a href="${pageContext.request.contextPath}/user/b"> 用户页面. </a></font>
			<br/>
		</security:authorize>
		<security:authorize permissionsUrl="/power/p" permissionsMethod="GET,All">
			<font size="3px"><a href="${pageContext.request.contextPath}/power/a"> 权限页面. </a></font>
			<br/>
		</security:authorize>
	</center>

	<div align="center" style="margin-top:59px">
		<table border="1">
			<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th>&nbsp;角色编号&nbsp;</th>
				<th>&nbsp;角色名&nbsp;</th>
				<security:authorize permissionsUrl="/role/r" permissionsMethod="PUT,HEAD,DELETE,All">
					<th>&nbsp;操作&nbsp;</th>
				</security:authorize>
			</tr>
			<tbody id="roleAuthority"></tbody>
		</table>
		<a href='javascript:radd_show()' >添加角色</a>
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
	
	<div id="roleWeightingDisplay">
		<div align="right">
			<button>
				<a href="javascript:radd_hide()">&nbsp;X&nbsp;</a>
			</button>
		</div> 
		<div align="center">
			<form action="<%=request.getContextPath() %>/role/r" method="post">
				<p align="left">添加角色</p>
				角色名:<input name="roleName" id="roname" type="text"/><br/>
				角色编号:<input name="roleCode" id="rocode" type="text"/><br/>
				<input name="mit" type="submit" id="mit"/>
				<input name="res" type="reset" id="res"/>
			</form>
		</div>
		</div>  
	<div id="roleEmpowermentConcealment"></div>
	    
	<div id="roleModificationDisplay">
		<div align="right">
			<button>
				<a href="javascript:role_hide()">&nbsp;X&nbsp;</a>
			</button>
		</div> 
		<div align="center">
			<form action="<%=request.getContextPath() %>/role/r" method="post">
				<p align="left">修改角色</p>
				<input name="id" id="rid" type="hidden"/><br/>
				角色名:<input name="roleName" id="rname" type="text"/><br/>
				角色编号:<input name="roleCode" id="rcode" type="text"/><br/>
				<input name="sub" type="submit" id="sub"/>
				<input name="res" type="reset" id="res"/>
				<input type="hidden" name="_method" value="PUT" />
			</form>
		</div>
		</div>  
	<div id="roleModificationHiding"></div>
	
	<form action="<%=request.getContextPath() %>/power/powerGivePower">
	<div id="rolePermissionWeightingDisplay">
		<div align="right">
			<button>
				<a href="javascript:power_hide()">&nbsp;X&nbsp;</a>
			</button>
		</div> 
		<div align="center">
			<p align="left">权限赋权</p>
			<p align='center' id='giveid'>
				<input name='sbm' type='submit'/>
			</p>
		</div>
		</div>  
	<div id="rolePermissionEmpowermentHiding"></div>
	</form>
	
	<form action="" method="POST" id="roleDataDeletion">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
	
</body>
<script type="text/javascript">

	$(document).ready(function(){
		
		init();
		
		deleteLoad();
		
		$.ajax({ // 加载所有权限
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/power/powerAll", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			success:function(list){
				var rech = "";
				var all = "";
				for(var i=0; i<list.length;i++){
					rech += "<input type='checkbox' name='checkname' value='"+list[i].id+"'/>"+list[i].powerName+ "&nbsp;&nbsp;&nbsp;";
				}
				all += rech+"<br/><br/><input id='sel_1' onchange='AllElectionOrCounterElection()' type='checkbox' value='1'/>全选/全不选";
				$("#rolePermissionWeightingDisplay").append(all);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
	        }
		});
	});

	function init(mark){
		var pageSize = "";
		var page = "";
		if(mark == 1){
			pageSize = $("#pageSizeId").val();
			page = 1;
		}else if(mark == 2){
			pageSize = $("#pageSizeId").val();
			var pageCount = $("#pageId").text();
			page = (parseInt(pageCount)-1);
			
		}else if(mark == 3){
			pageSize = $("#pageSizeId").val();
			var pageCount = $("#pageId").text();
			page = (parseInt(pageCount)+1);
		}else if(mark == 4){
			pageSize = $("#pageSizeId").val();
			page = $("#pageCountId").text();
		}else if(mark == 5){
			pageSize = $("#pageSizeId").val();
			page = 1;
		}
		
		$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/role/r", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{pageSize:pageSize,page:page},
			success:function(role){
				$("#roleAuthority").empty();
				var link = "";
				var list = role.t;
		
				$("#pageSizeCountId").text(role.pageSizeCount);
				$("#pageCountId").text(role.pageCount);
				$("#pageId").text(role.page);
				$("#pageSizeId").val(role.pageSize);
				
				if(role.page == 1){
					$("#shang").hide();
					$("#sho").hide();
				}else{
					$("#shang").show();
					$("#sho").show();
				}
				
				if(role.page == role.pageCount){
					$("#xia").hide();
					$("#wei").hide();
				}else{
					$("#xia").show();
					$("#wei").show();
				}
				
				for(var i in list){
					link +=  "<tr><td>"+list[i].id+"</td>"+
				 			"<td>"+list[i].roleName+"</td>"+
				 			"<td>"+list[i].roleCode+"</td>"+
					 		"<td>"+
					 		"<security:authorize permissionsUrl='/role/r' permissionsMethod='PUT,All'>"+
					 		"<a href='javascript:role_show("+list[i].id+")' >修改角色</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"<security:authorize permissionsUrl='/role/r' permissionsMethod='HEAD,All'>"+
					 		"<a href='javascript:power_show("+list[i].id+")' >权限赋权</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"<security:authorize permissionsUrl='/role/r' permissionsMethod='DELETE,All'>"+
					 		"<a href='javascript:void(0)' class='del' id='<%=request.getContextPath() %>/role/"+list[i].id+"'>删除角色</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"</td></tr>";
				}
				$("#roleAuthority").append(link);
				deleteLoad();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
	        }
		});
	}
	
	function deleteLoad(){
		$(".del").click(function() { // js 代码需要重新加载后才能使用,如果不使用函数或者调用则下次加载新数据后会点击无效(使用javascript就不会有问题( eg:onclick() ))
			if(confirm("确认删除吗?")){
				var dlt = $(this).attr("id");
				$("#roleDataDeletion").attr("action", dlt).submit();
				return false;
			}
		});
	}
	
	var ros = document.getElementById('roleWeightingDisplay');
	var roo = document.getElementById('roleEmpowermentConcealment');
    function radd_show()
    {  
    	ros.style.display = "block";
    	roo.style.display = "block";
    }  
    function radd_hide()
    {  
    	ros.style.display = "none";
    	roo.style.display = "none";
    }
    
    var rs = document.getElementById('roleModificationDisplay');
	var ro = document.getElementById('roleModificationHiding');
    function role_show(id)
    {  
    	$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/role/getRole", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{"id":id},
			success:function(role){
				$("#rid").val(role.id);
				$("#rname").val(role.roleName);
				$("#rcode").val(role.roleCode);
			}
		})
        rs.style.display = "block";
        ro.style.display = "block";
    }  
    function role_hide()
    {  
        rs.style.display = "none";
        ro.style.display = "none";
    }
    
    var poshow = document.getElementById('rolePermissionWeightingDisplay');
	var pohide = document.getElementById('rolePermissionEmpowermentHiding');
    function power_show(id)
    {  
    	$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  // 默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/power/rolePower", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{"id":id},
			success:function(role){
				$("#rolePermissionWeightingDisplay input[type='checkbox'").removeAttr('checked','checked');
				$("#giveid").append("<input type='hidden' value='"+id+"' id='thid' name='ids'>");
				for(var i=0;i<role.length;i++){
					$("#rolePermissionWeightingDisplay input[type='checkbox']").each(function(){ //所有的权限  each():循环值
						var value = $(this).val(); // 获取复选框中的值(此处是权限id)
						if(value == role[i].id)
						{
							$(this).prop("checked",true); // 如果相同就勾选上(默认值),因为先前清空了选中值  [prop:设置或返回被选元素的属性和值]
						}
					});
				}
			}
		})
        poshow.style.display = "block";
    	pohide.style.display = "block";
    }  
    function power_hide()
    {  
    	poshow.style.display = "none";
    	pohide.style.display = "none";
    }
    
    function AllElectionOrCounterElection(){
        var isCheck=$("#sel_1").is(':checked');  //获得全选复选框是否选中
        $("input[type='checkbox']").each(function() {  
            this.checked = isCheck;       //循环赋值给每个复选框是否选中
        });
    }
</script>
</html>