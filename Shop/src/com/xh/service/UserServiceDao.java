package com.xh.service;

import com.xh.domain.User;
import com.xh.exception.MessageException;

public interface UserServiceDao extends Service {
	/**
	 * ����û�
	 * @param user �û�ʵ��
	 * @throws MessageException 
	 */
	void addUser(User user) throws MessageException;
	/**
	 * �����û�����������û�
	 * @param username	�û���
	 * @param password	����
	 * @return	���ز��ҵ����û�
	 */
	User findUserByUneAndPwd(String username, String password);
	/**
	 * ���ռ����뼤���û�
	 * @param activecode ������
	 * @return	���ؼ�����û�
	 * @throws MessageException 
	 */
	User activeUser(String activecode) throws MessageException;
	/**
	 * ��ѯ��ǰ�û���ɫ
	 * @return	���ص�ǰ�û���ɫ
	 */
	String findUserRoleByUid(String uid);

}
