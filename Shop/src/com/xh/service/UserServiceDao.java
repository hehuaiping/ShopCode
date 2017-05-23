package com.xh.service;

import com.xh.domain.User;
import com.xh.exception.MessageException;

public interface UserServiceDao extends Service {
	/**
	 * 添加用户
	 * @param user 用户实体
	 * @throws MessageException 
	 */
	void addUser(User user) throws MessageException;
	/**
	 * 根据用户名密码查找用户
	 * @param username	用户名
	 * @param password	密码
	 * @return	返回查找到的用户
	 */
	User findUserByUneAndPwd(String username, String password);
	/**
	 * 按照激活码激活用户
	 * @param activecode 激活码
	 * @return	返回激活的用户
	 * @throws MessageException 
	 */
	User activeUser(String activecode) throws MessageException;
	/**
	 * 查询当前用户角色
	 * @return	返回当前用户角色
	 */
	String findUserRoleByUid(String uid);

}
