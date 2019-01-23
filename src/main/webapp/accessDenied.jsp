<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无权页面</title>
</head>
<style> 
	a{ text-decoration:none;}
	/* css注释： 鼠标经过热点文字被加下划线 */ 
	a:hover{ text-decoration:underline;}
	a{color:#000}
</style> 
<body>

	<div align="center" style="margin-top:59px">
		<font size="6" color="red"> 抱歉,您的权限不够!!! </font>
		<br/>
		<a href='<c:url value="/logout" />'>点击此处退出登录.</a>
	</div>
</body>
</html>