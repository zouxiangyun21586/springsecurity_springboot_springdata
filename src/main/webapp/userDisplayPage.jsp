<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%> <!-- 用于跳转jsp时 PUT方法,页面报错 -->
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<style type="text/css">
		 #userModifiedDataDisplay,#userEmpowermentDisplay {  
			display:none;  
			border:1em solid Azure; /* 弹出层边框颜色与宽度  */
			height:50%;  
			width:24%;  
			position:absolute; /*让节点脱离文档流,我的理解就是,从页面上浮出来,不再按照文档其它内容布局*/  
			top:20%; /*节点脱离了文档流,如果设置位置需要用top和left,right,bottom定位*/  
			left:35%;  
			z-index:2; /*个人理解为层级关系,由于这个节点要在顶部显示,所以这个值比其余节点的都大*/  
			background: AliceBlue;  
		} 
	
		#userModifiedDataHiding,#userEmpowermentHiding {  
			width: 100%;  
			height: 100%;  
			opacity:0.3; /* 设置背景色透明度,1为完全不透明,IE需要使用filter:alpha(opacity=80); */  
			filter:alpha(opacity=80);  
			display: none;
			position:absolute;  
			top:0;  
			left:0;  
			z-index:1;  
			background: silver;  
		}
		
		/* a 标签下划线隐藏并且字体颜色为 黑色 */
		a{ text-decoration:none;color:#000;}
		/* css注释： 鼠标经过热点文字被加下划线 */ 
		a:hover{ text-decoration:underline;}
		
		table {
			border-collapse:collapse;
			text-align:center; 
		}
		
	</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-1.9.1.min.js"></script>
<body background="${pageContext.request.contextPath}/images/banner.jpg">
	<center>
		<security:authorize permissionsUrl="/role/r" permissionsMethod="GET,All">
			<br/><br/>
			<font size="3px"><a href="${pageContext.request.contextPath}/role/a"> 角色页面. </a></font>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</security:authorize>
		<security:authorize permissionsUrl="/power/p" permissionsMethod="GET,All">
			<font size="3px"><a href="${pageContext.request.contextPath}/power/a"> 权限页面. </a></font>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</security:authorize>
		<font size="3px"><a href="${pageContext.request.contextPath}/addAccount"> 注册账号. </a></font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</center>

	<div align="center" style="margin-top:59px">
		<table border="1" cellspacing="1">
			<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th>&nbsp;账号&nbsp;</th>
				<th>&nbsp;用户名&nbsp;</th>
				<th>&nbsp;密码&nbsp;</th>
				<security:authorize permissionsUrl="/role/r" permissionsMethod="GET,All">
					<th>&nbsp;角色&nbsp;</th>
				</security:authorize>
				<security:authorize permissionsUrl="/user/u" permissionsMethod="PUT,HEAD,DELETE,All">
					<th>&nbsp;操作&nbsp;</th>
				</security:authorize>
			</tr>
			<tbody id="link"></tbody>
		</table>
		
		<a href="javascript:void(0)" onclick="init(1)" id="sho">首页</a>
		<a href="javascript:void(0)" onclick="init(2)" id="shang">上页</a>
		<a href="javascript:void(0)" onclick="init(3)" id="xia">下页</a>
		<a href="javascript:void(0)" onclick="init(4)" id="wei">尾页</a> &nbsp;&nbsp;&nbsp;
		<br/>
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
	<br/>
	<center>
		<a href='<c:url value="/logout" />'>点击此处退出登录.</a>
	</center>
	
	
	<form action="" method="POST" id="userDeleting">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
	
	<form action="<%=request.getContextPath() %>/role/roleGivePower">
		<div id="userEmpowermentDisplay">
			<div align="right">
				<button>
					<a href="javascript:role_hide()">&nbsp;X&nbsp;</a>
				</button>
			</div> 
			<div align="center">
				<p align="left">角色赋权</p>
				<p align='center' id='roleEmpowermentId'>
					<input name="roleEmpowermentSubmission" type="submit"/><br/>
				</p>
			</div>
			</div>  
		<div id="userEmpowermentHiding"></div>
	</form>
	
	<div id="userModifiedDataDisplay">
		<div align="right">
			<button>
				<a href="javascript:user_hide()">&nbsp;X&nbsp;</a>
			</button>
		</div> 
		<div align="center">
			<form action="<%=request.getContextPath() %>/user/u" method="post">
				<p align="left">修改用户</p>
				<input type="hidden" name="id" id="uid" />
				<input type="hidden" name="account" id="uacc" />
				用户名: <input name="userName" type="text" id="uname"/><br/>
				<!-- 密&nbsp;&nbsp;&nbsp;码:<input name="passWord" type="text" id="upass"/><br/> -->
				<input name="sub" type="submit" id="sub"/>
				<input type="hidden" name="_method" value="PUT" />
			</form>
		</div>
	</div>
	<div id="userModifiedDataHiding"></div>
</body>
<script type="text/javascript">

	$(document).ready(function(){
		init();
		loader();
		
		$.ajax({ // 将所有角色先查询出来,以便于用户赋权与修改
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/role/roleAll", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			success:function(list){
				var ch = "";
				var all = "";
				for(var i = 0;i<list.length;i++)
				{
					ch += "<input type='checkbox' name='checkname' value='"+list[i].id+"'>"+list[i].roleName + "&nbsp;&nbsp;&nbsp;";
				}
				all += ch+"<br/><br/><input id='sel_1' onchange='AllElectionOrCounterElection()' type='checkbox' value='1'/>全选/全不选";
				$("#userEmpowermentDisplay").append(all);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
	        }
		});
	});
	
	var roshow = document.getElementById('userEmpowermentDisplay');
	var rohide = document.getElementById('userEmpowermentHiding');
    function role_show(id)
    {  
    	$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/role/userRole", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{"id":id},
			success:function(userRoleData){
				// 清空所有选中属性,如果不清空就会导致点击下一个是查出来的角色也会有上一个用户中的角色
				$("#userEmpowermentDisplay input[type='checkbox'").removeAttr('checked','checked');
				
				$("#roleEmpowermentId").append("<input type='hidden' value='"+id+"' id='thid' name='ids'>");
				for(var i = 0;i<userRoleData.length;i++)//返回用户的权限
				{
					//把用户查询出来的角色，与我们所有的角色复选框逐一进行比较，如果值相同就就进行选中。
					
					$("#userEmpowermentDisplay input[type='checkbox']").each(function(){// 循环选中的checkbox（复选框）
						var value = $(this).val(); // 获取复选框中的值(此处是角色id)
						if(value == userRoleData[i].id)
						{
							$(this).prop("checked",true); // 如果相同就勾选上(默认值),因为先前清空了选中值
						}
					});	
				}
			}
		});
    	roshow.style.display = "block";
    	rohide.style.display = "block";
    }  
    function role_hide()
    {  
    	roshow.style.display = "none";
    	rohide.style.display = "none";
    }
	
	
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
			url:"<%=request.getContextPath() %>/user/u", // 发送请求的地址
			scriptCharset: 'utf-8',
			data:{pageSize:pageSize,page:page},
			dataType:"json",
			success:function(user){
				$("#link").empty();  // 将表格中的值清空
				var receive = "";
				var list = user.t;
				
				$("#pageSizeCountId").text(user.pageSizeCount);
				$("#pageCountId").text(user.pageCount);
				$("#pageId").text(user.page);
				$("#pageSizeId").val(user.pageSize);
				
				if(user.page == 1){
					$("#shang").hide();
					$("#sho").hide();
				}else{
					$("#shang").show();
					$("#sho").show();
				}
				
				if(user.page == user.pageCount){
					$("#xia").hide();
					$("#wei").hide();
				}else{
					$("#xia").show();
					$("#wei").show();
				}
				
				for(var i in list){
					receive +=  "<tr><td>"+list[i].id+"</td>"+
				 			"<td>"+list[i].account+"</td>"+
				 			"<td>"+list[i].userName+"</td>"+
				 			"<td>"+list[i].passWord+"</td>"+
				 			"<security:authorize permissionsUrl="/role/r" permissionsMethod="GET,All">"+
				 			"<td id='roname'>"+list[i].roleSet+"</td>"+
				 			"</security:authorize>"+
				 			"<security:authorize permissionsUrl='/user/u' permissionsMethod='PUT,HEAD,DELETE,All'>"+
					 		"<td>"+
					 		"<security:authorize permissionsUrl='/user/u' permissionsMethod='PUT,All'>"+
					 		"<a href='javascript:user_show("+list[i].id+")' >修改用户</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"<security:authorize permissionsUrl='/user/u' permissionsMethod='HEAD,All'>"+
					 		"<a href='javascript:role_show("+list[i].id+")'>角色赋权</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"<security:authorize permissionsUrl='/user/u' permissionsMethod='DELETE,All'>"+
					 		"<a href='javascript:void(0)' class='del' id='<%=request.getContextPath() %>/user/"+list[i].id+"'>删除用户</a> &nbsp;"+
					 		"</security:authorize>"+
					 		"</td></security:authorize>"+
					 		"</tr>";
				}
				$("#link").append(receive);
				loader();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				
				alert("当前状态: "+XMLHttpRequest.readyState);
				alert("HTTP状态码: "+XMLHttpRequest.status);
				alert("错误信息: "+errorThrown)
				alert("状态: "+textStatus);
	        }
		});
	}
	
	var us = document.getElementById('userModifiedDataDisplay');
	var uo = document.getElementById('userModifiedDataHiding');
    function user_show(id)
    {  
    	$.ajax({
			type: "get",  // 请求方式(post或get)
			async:false,  //默认true(异步请求),设置为false(同步请求)
			url:"<%=request.getContextPath() %>/user/get", // 发送请求的地址
			scriptCharset: 'utf-8',
			dataType:"json",
			data:{"id":id},
			success:function(ujson){
				$("#uid").val(ujson.id);
				$("#uname").val(ujson.userName);
				$("#uacc").val(ujson.account); // val(aa) 赋值
				$("#upass").val(ujson.passWord); // 赋值
			}
		});
        us.style.display = "block";
        uo.style.display = "block";
    }  
    function user_hide()
    {  
        us.style.display = "none";
        uo.style.display = "none";
    }
	
	function loader(){
		$(".del").click(function() {
			if(confirm("确认删除吗?")){
				var dlt = $(this).attr("id"); // attr() 方法设置或返回被选元素的属性值
				$("#userDeleting").attr("action", dlt).submit();
				return false;
			}
		});
	}
	
	function AllElectionOrCounterElection(){
        var isCheck=$("#sel_1").is(':checked');  //获得全选复选框是否选中
        $("input[type='checkbox']").each(function() {  
            this.checked = isCheck;       //循环赋值给每个复选框是否选中
        });
    }
	
</script>
</html>