<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Single Page App</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
<style>
.save {
	display:none
}
</style>
</head>
<body>

<jsp:useBean id="obj" class="com.demo.dao.UserDAO" />

	<div class="container">
		
		<table class="table table-bordered table-striped table-hover table-condensed">
			<tr class="warning">
				<th>ID</th>
				<th>Username</th>
				<th>Password</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			
			<%
				out.println(obj.getUser());
			
			%>
			
		</table>
		
		<div id="addnew">
			<img src="images/add.png"/>
			<br/>
		</div>
	</div>

	
	<script src="script.js"></script>
</body>
</html>