package com.xh.dao;

import java.util.List;
import java.util.Map;

import com.xh.domain.OrderItem;
import com.xh.domain.Orders;

public interface OrderDao extends Dao {
	/**
	 * ��Ӷ���
	 * @param orders
	 */
	void addOrders(Orders orders);
	/**
	 * ��Ӷ�����
	 * @param item
	 */
	void addOrderItem(OrderItem item);
	/**
	 * �޸��ջ���Ϣ
	 * @param orders ��װ���ջ���Ϣ��orders
	 */
	void updateOrdersAddress(Orders orders);
	/**
	 * �޸�֧��״̬
	 * @param r6_Order
	 * @param i
	 */
	void updateState(String r6_Order, int i);
	/**
	 * �����û�ID��ѯ�����ж���
	 * @param uid	�û�ID
	 * @return	���ص�ǰ�û����еĶ���
	 */
	List<Orders> findOrdersByUid(String uid);
	/**
	 * ���ݶ���ID��ѯ�����еĶ�����
	 * @param oid	����ID	
	 * @return	���ظö������еĶ�����
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * ���ݶ�����IDɾ��������
	 * @param itemid	������ID
	 */
	void deleteOrderItemById(String itemid);
	/**
	 * ���ݶ���IDɾ������
	 * @param oid
	 */
	void deleteOrdersByOid(String oid);

}
