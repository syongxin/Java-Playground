/**
 * 
 */
package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.model.UserBean;

/**
 * @author yongxinsun
 *
 */
public class UserDAO {
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	Statement st;
	
	public UserDAO(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/singlejsp","root","syx1224syx");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean validateUser(String username) throws SQLException{
		boolean status = false;
		ps = con.prepareStatement("select * from user where username = ?");
		ps.setString(1, username);
		rs = ps.executeQuery();
		if(!rs.next()){
			status = true;
		}
		
		return status;
	}
	
	
	public void inserUser(String username, String password) throws SQLException{
		ps = con.prepareStatement("insert into user values (default, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.executeUpdate();
	}
	
	public void deleteUser(int id) throws SQLException{
		ps = con.prepareStatement("delete from user where id = ?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}
	
	public void updateUser(int id, String username, String password) throws SQLException{
		ps = con.prepareStatement("update user set username = ?, password = ? where id = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setInt(3, id);
		ps.executeUpdate();
	}
	
	public void updateUser(int id, String password) throws SQLException{
		ps = con.prepareStatement("update user set password = ? where id = ?");
		ps.setString(1, password);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
	
	public String getUser() throws SQLException{
		ps = con.prepareStatement("select * from user;");
		rs = ps.executeQuery();
		
		
		String html = "";
		while(rs.next()){
			html += "<tr id='" + rs.getInt(1) + "'>"
				   +"<td>" + rs.getInt(1) + "</td>"
				   +"<td>" + rs.getString(2) + "</td>"
				   +"<td>" + rs.getString(3) + "</td>"
				   +"<td><img class='edit' src='images/edit.png' id='edit_"+rs.getInt(1)+"'/>"
				   +"<img class='save' src='images/save.png' id='save_"+rs.getInt(1)+"'/></td>"
				   +"<td><a href='delete.jsp?id="+ rs.getString(1) +"'><img src='images/delete.png'/></a></td>"
				   +"</tr>";	   
		}
		return html;
	}
	
}
