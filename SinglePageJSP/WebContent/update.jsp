<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update</title>
</head>
<body>
<jsp:useBean id="obj" class="com.demo.dao.UserDAO" />

<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	int id = Integer.parseInt(request.getParameter("id"));
	String same_user = request.getParameter("sameuser");
	
	if(same_user.equals("false")){
		if(obj.validateUser(username)){
			obj.updateUser(id, username, password);
			response.sendRedirect("index.jsp");
		}else{
%>
	    	 <script>
        		alert("Username already exists!" );
        		window.location = 'index.jsp';
   			</script> 
<% 
		}
	}else{
		obj.updateUser(id, password);
		response.sendRedirect("index.jsp");
	}
%>

</body>
</html>