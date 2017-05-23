package com.xh.dao;

import com.xh.domain.User;

public interface UserDao extends Dao {
	/**
	 * ����û�
	 * @param user
	 */
	void addUser(User user);
	
	/**
	 * �����û�����������û�
	 * @param username �û���
	 * @param password ����
	 * @return	���ز��ҵ����û�
	 */
	User findUserByUneAndPwd(String username, String password);
	/**
	 * �����û��������û�
	 * @param username
	 * @return
	 */
	User findUserByUserName(String username);
	/**
	 * ���ݼ���������û�
	 * @param activecode ������
	 * @return	���ض�Ӧ��User
	 */
	User findUserByActiveCode(String activecode);
	/**
	 * ɾ���û�
	 * @param user	user����
	 */
	void deleteUser(User user);
	/**
	 * �����û�
	 * @param user user����
	 */
	void activeUser(String code);
	/**
	 * ��ѯ�û���ɫ
	 * @param uid	�û�ID
	 * @return	�����û���ɫ
	 */
	String findUserRoleByUid(String uid);

}
