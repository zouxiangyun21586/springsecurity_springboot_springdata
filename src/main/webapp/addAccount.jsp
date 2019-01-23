<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加账户</title>
<style type="text/css">

	.button {
	    background-color: #4CAF50; /* Green */
	    border: none;
	    color: white;
	    padding: 10px 25px;
	    text-align: center;
	    text-decoration: none;
	    display: inline-block;
	    font-size: 16px;
	    margin: 4px 2px;
	    cursor: pointer;
	    width:300px;height:44px;margin-top:25px;
	    border-radius: 8px; /* 圆角  */
	}
	
	.button2 {background-color: #008CBA;} /* Red */
	
	.button3 {background-color: #C67171;} /* Red */

	a {
		text-decoration: none;
		color: #000
	}
	
	:-moz-placeholder {
	   color:    #909;
	   opacity:  1;
	}
	
	#div {
		margin-top:59px;
	}
		
	input {
		width:300px;
		height:42px;
		margin-top:25px;
		line-height:42px;
	} 
</style>
</head>
<body bgcolor="#A2B5CD">
	<div id="div" align="center">
		<h1><font color="#FFC1C1">SpringSecurity 注册/添加页面</font></h1>
		<form action="${pageContext.request.contextPath}/user/add" method="post" align="center" name="formsub" onsubmit="return check()">
			<input type="text" name="account" id="account" placeholder="请输入账号" autocomplete="off" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff"/>
			<form:errors path="account"></form:errors>
			<br/>
			<input type="text" name="userName" id="userName" placeholder="请输入用户名" autocomplete="off" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff"/>
			<form:errors path="userName"></form:errors>
			<br/>
			<input type="password" name="passWord" id="passWord" placeholder="请输入密码" autocomplete="off" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff"/>
			<form:errors path="password"></form:errors>
			<br/>
			<input type="password" name="passWord1" id="passWord1" placeholder="请再次输入密码" autocomplete="off" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff"/>
			<form:errors path="passWord1"></form:errors>
			<br/>
			<button type="submit" id="submit" class="button" >提交</button>
			<br/>
			<button type="reset" id="reset" class="button button2" >重置</button>
			<br/>
			<button type="button" class="button button3" >
				<a href='<c:url value="/logout" />' >返回</a>
			</button>
			<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" />
		</form>
	</div>
</body>
<script type="text/javascript">
	function check(){
		var account = document.formsub.account.value;
		var username = document.formsub.userName.value;
		var password = document.formsub.passWord.value;
		var password1 = document.formsub.passWord1.value;
		if (username==""|| password==""||password1==""||account==""){
			alert("任意信息不能为空，请重新填写！");
			return false;
		}else if(username.length>12){
			alert("用户名不能超过12个字符,请重新输入！");
			return false;
		}else if(password.length<4){
			alert("密码不能小于4个字符,请重新输入！");
			return false;
		}else if (password!=password1){
			alert("两次密码输入不一致,请重新输入!");
			return false;
		}else{
			return true;
		}
	}
</script>
</html>