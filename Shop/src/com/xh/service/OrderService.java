package com.xh.service;

import java.util.List;

import com.xh.annotation.Tran;
import com.xh.domain.Orders;

public interface OrderService extends Service {
	/**
	 * 生成订单
	 * @param orders 封装了订单信息的对象
	 */
	@Tran
	void submitOrder(Orders orders);
	/**
	 * 更新收货人信息
	 * @param orders
	 */
	void updateOrdersAddress(Orders orders);
	/**
	 * 修改订单状态
	 * @param r6_Order
	 * @param i
	 */
	void updateState(String r6_Order, int i);
	/**
	 * 根据用户Id查询当前用户下所有的订单
	 * @param uid	用户ID
	 * @return	返回当前用户的所有订单
	 */
	List<Orders> findOrdersByUid(String uid);
	/**
	 * 根据订单项ID删除ID
	 * @param itemid	订单项ID
	 */
	void deleteOrderByOid(String oid);

}
