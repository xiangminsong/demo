<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>已经进入到user页面....</h1>
	<form action="${pageContext.request.contextPath }/demo/add" method = "post">
		<tr>
			<td>name</td>
			<td><input type="text" name="name"/></td>
		</tr><br/>
		<tr>
			<td>age</td>
			<td><input type="text" name="age"/>
		</tr><br/>
		<tr>
			<td><input type="submit" value="提交"/>
		</tr>
	</form>
</body>
</html>