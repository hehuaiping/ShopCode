package com.xh.service;

import java.util.List;

import com.xh.annotation.Tran;
import com.xh.domain.Orders;

public interface OrderService extends Service {
	/**
	 * ���ɶ���
	 * @param orders ��װ�˶�����Ϣ�Ķ���
	 */
	@Tran
	void submitOrder(Orders orders);
	/**
	 * �����ջ�����Ϣ
	 * @param orders
	 */
	void updateOrdersAddress(Orders orders);
	/**
	 * �޸Ķ���״̬
	 * @param r6_Order
	 * @param i
	 */
	void updateState(String r6_Order, int i);
	/**
	 * �����û�Id��ѯ��ǰ�û������еĶ���
	 * @param uid	�û�ID
	 * @return	���ص�ǰ�û������ж���
	 */
	List<Orders> findOrdersByUid(String uid);
	/**
	 * ���ݶ�����IDɾ��ID
	 * @param itemid	������ID
	 */
	void deleteOrderByOid(String oid);

}
