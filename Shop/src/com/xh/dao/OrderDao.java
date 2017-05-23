package com.xh.dao;

import java.util.List;
import java.util.Map;

import com.xh.domain.OrderItem;
import com.xh.domain.Orders;

public interface OrderDao extends Dao {
	/**
	 * 添加订单
	 * @param orders
	 */
	void addOrders(Orders orders);
	/**
	 * 添加订单项
	 * @param item
	 */
	void addOrderItem(OrderItem item);
	/**
	 * 修改收货信息
	 * @param orders 封装了收货信息的orders
	 */
	void updateOrdersAddress(Orders orders);
	/**
	 * 修改支付状态
	 * @param r6_Order
	 * @param i
	 */
	void updateState(String r6_Order, int i);
	/**
	 * 根据用户ID查询出所有订单
	 * @param uid	用户ID
	 * @return	返回当前用户所有的订单
	 */
	List<Orders> findOrdersByUid(String uid);
	/**
	 * 根据订单ID查询出所有的订单项
	 * @param oid	订单ID	
	 * @return	返回该订单所有的订单项
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * 根据订单项ID删除订单项
	 * @param itemid	订单项ID
	 */
	void deleteOrderItemById(String itemid);
	/**
	 * 根据订单ID删除订单
	 * @param oid
	 */
	void deleteOrdersByOid(String oid);

}
