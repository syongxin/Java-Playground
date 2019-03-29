<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new</title>
</head>
<body>
<jsp:useBean id="obj" class="com.demo.dao.UserDAO" />

<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	if(obj.validateUser(username)){
		obj.inserUser(username, password);
		response.sendRedirect("index.jsp");
	} else {
%>

	     <script>
         	alert("Username already exists!" );
         	window.location = 'index.jsp';
    	</script> 
<% 
	}
%>

</body>
</html>