package com.xh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xh.domain.Category;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;

public interface AdminService extends Service {
	/**
	 * ������Ʒ��������
	 * @return
	 */
	List<Category> findAllCategory();
	/**
	 * �����Ʒ
	 * @throws SQLException 
	 */
	void addProduct(Product product) throws SQLException;
	/**
	 * �������ж���
	 * @return
	 */
	List<Orders> findAllOrders();
	/**
	 * ���ݶ���ID��ѯ�ö����µ����ж�����
	 * @param oid	����ID
	 * @return
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * �����û�����������û�
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByNaAndPwd(String username, String password);
	/**
	 * ��ѯ������Ʒ
	 * @return	����������Ʒ��ɵļ���
	 */
	List<Product> findAllProduct();
	/**
	 * ������Ʒ��Ϣ
	 * @param product	��װ��������Ʒ��Ϣ��bean
	 */
	void updateProduct(Product product);
	/**
	 * ������ƷID��ѯ��Ʒ
	 * @param pid	��ƷID
	 * @return	���ط�װ����Ʒ��Ϣ�ļ���
	 */
	Map<String, Object> findProductByPid(String pid);
	/**
	 * ������Ʒ����ID��ѯ��Ʒ����
	 * @param cid	��ƷID
	 * @return	���ظ�ID��Ʒ������
	 */
	Category findCategoryByCid(String cid);
	/**
	 * ������Ʒ����Idɾ����Ʒ����
	 * @param cid	��Ʒ����ID
	 * @return
	 */
	void deleteCategoryByCid(String cid);
	/**
	 * �����Ʒ����
	 * @param category ��װ����Ʒ���������
	 */
	void addCategory(Category category);
	/**
	 * ������Ʒ��������
	 * @param category ��װ��������Ʒ�������ݵ�bean
	 */
	void updateCategoryByCid(Category category);
	/**
	 * ������ƷIDɾ����Ʒ
	 * @param pid	��ƷID
	 */
	void deleteProducyByPid(String pid);

}
