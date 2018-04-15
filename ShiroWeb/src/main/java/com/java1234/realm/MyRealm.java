package com.java1234.realm;

import java.sql.Connection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.java1234.dao.UserDao;
import com.java1234.entity.User;
import com.java1234.util.DbUtil;

public class MyRealm extends AuthorizingRealm{
/* 
 * 为当前登录的用户授予角色和权限
 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
 */
	private UserDao userDao=new UserDao();
	private DbUtil dbUtil=new DbUtil();
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		String username=(String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		Connection con=null;
		try {
			con=dbUtil.getCon();
			authorizationInfo.setRoles(userDao.getRoles(con,username));
		    authorizationInfo.setStringPermissions(userDao.getPermissions(con,username));
		}catch(Exception e) {e.printStackTrace();}
		finally {try{dbUtil.closeCon(con);}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
		return authorizationInfo;
	}
/**
 * 验证当前登录的用户
 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username=(String)token.getPrincipal();
		Connection con=null;
		try {
			con=dbUtil.getCon();
			User user=userDao.getByUserName(con,username);
			if(user!=null) {
				AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"xx");
			return authcInfo;
			}
			else return null;
		}
		catch(Exception e) {e.printStackTrace();}
		finally {try{dbUtil.closeCon(con);}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
		return null;
	}

}
