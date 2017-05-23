package com.xh.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xh.domain.User;
import com.xh.utils.TransactionManager;

public class UserDaoImpl implements UserDao {
	
	public void addUser(User user) {
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			String sql = "insert into user value(?,?,?,?,?,?,?,?,?,?,null)";
			runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public User findUserByUneAndPwd(String username, String password) {
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			String sql = "select * from user where username=? and password=?";
			return runner.query(sql,new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return null;
	}

	public User findUserByUserName(String username) {
		String sql  = "select * from user where username=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return runner.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User findUserByActiveCode(String activecode) {
		String sql  = "select * from user where code=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return runner.query(sql, new BeanHandler<User>(User.class),activecode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUser(User user) {
		String sql = "delete from user where uid=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			runner.update(sql,user.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void activeUser(String code) {
		String sql = "update user set state=1 where code=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			runner.update(sql,code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String findUserRoleByUid(String uid) {
		String sql = "select role from user where uid=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return (String) runner.query(sql, new ScalarHandler() ,uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
