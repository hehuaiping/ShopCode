package com.xh.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xh.domain.Category;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;

public interface AdminDao extends Dao {
	/**
	 * ������Ʒ��������
	 * @return
	 */
	List<Category> findAllCategory();
	/**
	 * �����Ʒ
	 * @param product
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
	 * @return	���ظö����µ����ж�����
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * �����û��������ѯ�û�
	 * @param username	�û���
	 * @param password	����
	 * @return	���ز��ҵ����û�
	 */
	User findUserByNaAndPwd(String username, String password);
	/**
	 * ��ѯ������Ʒ
	 * @return	����������Ʒ����
	 */
	List<Product> findAllProduct();
	/**
	 * ������Ʒ��Ϣ
	 * @param product ��װ��������Ʒ���ݵ�bean
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
	 * @param cid	��Ʒ����ID
	 * @return	���ظ�ID����Ʒ����
	 */
	Category findCategoryByCid(String cid);
	/**
	 * ������Ʒ����IDɾ����Ʒ
	 * @param cid
	 */
	void deleteCategoryByCid(String cid);
	/**
	 * �����Ʒ����
	 * @param category ��װ����Ʒ�����ʵ��
	 */
	void addCategory(Category category);
	/**
	 * ������Ʒ��������
	 * @param category ��װ��������Ʒ��������ݵ�bean
	 */
	void updateCategoryByCid(Category category);
	/**
	 * ������ƷID��ѯ�йص�ǰ��Ʒ�����ж�����
	 * @param pid ��ƷID
	 * @return	���ع��ڵ�ǰ��Ʒ�����ж�����
	 */
	List<OrderItem> findOrderItemByPid(String pid);
	/**
	 * ���ݶ�����Id,ɾ���ö�����
	 * @param itemid ������Id
	 */
	void deleteOrderItemByItemId(String itemid);
	/**
	 * ������ƷIDɾ������Ʒ
	 * @param pid	��ƷID
	 */
	void deleteProductByPid(String pid);

}
