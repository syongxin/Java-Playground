/**
 * 
 */
package com.cartlist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cartlist.model.UserBean;

/**
 * @author yongxinsun
 *
 */
public class UserDAO {
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	
	public UserDAO(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/cartlist","root","syx1224syx");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateUser(UserBean user){
		boolean status = false;
		try{
			ps = con.prepareStatement("select * from user where username = ?");
			ps.setString(1, user.getUsername());
			rs = ps.executeQuery();
			if(!rs.next()){
				status = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
	
	public void insertUser(UserBean user){
		try{
			ps = con.prepareStatement("insert into user values(?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean loginUser(UserBean user){
		boolean status = false;
		
		try{
			ps = con.prepareStatement("select * from user where username=? and password=?");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			rs = ps.executeQuery();
			if(rs.next()){
				status = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
}
