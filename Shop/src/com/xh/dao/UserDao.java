package com.xh.dao;

import com.xh.domain.User;

public interface UserDao extends Dao {
	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(User user);
	
	/**
	 * 按照用户名密码查找用户
	 * @param username 用户名
	 * @param password 密码
	 * @return	返回查找到的用户
	 */
	User findUserByUneAndPwd(String username, String password);
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findUserByUserName(String username);
	/**
	 * 根据激活码查找用户
	 * @param activecode 激活码
	 * @return	返回对应的User
	 */
	User findUserByActiveCode(String activecode);
	/**
	 * 删除用户
	 * @param user	user对象
	 */
	void deleteUser(User user);
	/**
	 * 激活用户
	 * @param user user对象
	 */
	void activeUser(String code);
	/**
	 * 查询用户角色
	 * @param uid	用户ID
	 * @return	返回用户角色
	 */
	String findUserRoleByUid(String uid);

}
