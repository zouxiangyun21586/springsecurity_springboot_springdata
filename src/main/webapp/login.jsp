<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<title>登录页面</title>
	</head>
<style> 
	a{ text-decoration:none;}
	/* css注释： 鼠标经过热点文字被加下划线 */ 
	a:hover{ text-decoration:underline;}
	
	a{color:#000}
	
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
	
	#div {
		margin-top:59px;
	}
		
	input {
		width:300px;
		height:42px;
		margin-top:25px;
		line-height:42px;
	} 
	
	form {position:relative;width:305px;margin:15px auto 0 auto;text-align:center}
	
	:-moz-placeholder {
	   color:    #909;
	   opacity:  1;
	}

</style>
<body bgcolor="AntiqueWhite">

	<div id="div" align="center">
		<!-- 注意: 这里是post请求(默认是post不能改动) -->
		<h1><font color="#9999CC">SpringSecurity 登录页面</font></h1>
		<font size="6" color="red">${error }</font><br/>
		<form action="${pageContext.request.contextPath}/spring_security_check_AA" method="POST" >
			<div>
				<input type="text" name="account" id="account" placeholder="Account" autocomplete="off" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff"/>
				<form:errors path="account"></form:errors>
				<br/>
			</div>
			<div>
				<input type="password" name="password" id="password" placeholder="Password" oncontextmenu="return false" onpaste="return false" style="background:transparent;padding-left:10px; font-size:16px;border:1px solid #ffffff" />
				<form:errors path="password"></form:errors>
				<br/>
			</div>
			<button id="submit" type="submit" class="button">登 陆</button>  
			<br/>
			<button id="reset" type="reset" class="button button2">重置</button>
		</form>
		<button type="button" class="button button3" >
			<a href="${pageContext.request.contextPath}/addAccount"> 注册账号. </a>
		</button>
		<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" />
	</div>
</body>
</html>