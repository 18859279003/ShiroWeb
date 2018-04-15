package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.java1234.entity.User;

public class UserDao {
public User getByUserName(Connection con,String username) throws Exception{
	User resultUser=null;
	String sql="select *from t_user where username=?";
	PreparedStatement pstmt=con.prepareStatement(sql);
pstmt.setString(1, username);
ResultSet rs=pstmt.executeQuery();
if(rs.next()) {
	resultUser=new User();
	resultUser.setId(rs.getInt("id"));
	resultUser.setUsername(rs.getString("username"));
	resultUser.setPassword(rs.getString("password"));
}
return resultUser;
}

public Set<String> getRoles(Connection con, String username) throws Exception {
	// TODO Auto-generated method stub
	Set<String>roles=new HashSet<String>();
	String sql="select * from t_user u,t_role r where u.roleId=r.id and u.roleId=r.id and u.username=?";
	PreparedStatement pstmt=con.prepareStatement(sql);
	pstmt.setString(1, username);
	ResultSet rs=pstmt.executeQuery();
	while(rs.next()) {roles.add(rs.getString("roleName"));}
	return roles;
}

public Set<String> getPermissions(Connection con, String username) throws Exception {
	// TODO Auto-generated method stub
	Set<String>permissions=new HashSet<String>();
	String sql="select * from t_user u,t_role r,t_permission p where u.roleId=r.id and p.roleId=r.id and u.username=?";
	PreparedStatement pstmt=con.prepareStatement(sql);
	pstmt.setString(1, username);
	ResultSet rs=pstmt.executeQuery();
	while(rs.next()) {permissions.add(rs.getString("permissionname"));}
	return permissions;
}
}
