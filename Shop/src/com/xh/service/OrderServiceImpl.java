package com.xh.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.xh.dao.OrderDao;
import com.xh.dao.ProductDao;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;

public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = BasicFactory.getBasicFactory().getDao(OrderDao.class);
	ProductDao prodDao = BasicFactory.getBasicFactory().getDao(ProductDao.class);
	
	public void submitOrder(Orders orders) {
		//1、调用orderDao中添加订单的方法
		orderDao.addOrders(orders);
		//2、调用orderDao中添加订单项的方法,可能添加1次多次
		//获取订单项
		List<OrderItem> orderList = orders.getOrderItem();
		for(OrderItem item : orderList) {
			//调用orderDao中添加订单项的方法
			orderDao.addOrderItem(item);
			//3、从商品表中扣除库存数量
			prodDao.reductPnum(item.getProduct().getPid(),item.getCount());
		}

	}

	public void updateOrdersAddress(Orders orders) {
		orderDao.updateOrdersAddress(orders);
	}

	public void updateState(String r6_Order, int i) {
		orderDao.updateState(r6_Order,i);
	}

	public List<Orders> findOrdersByUid(String uid) {
		try {
			//封装orders
			//1、根据当前用户ID查询出所有订单
			List<Orders> ordersList = orderDao.findOrdersByUid(uid);
			//2、根据orders表查询出所有订单项表
			for(Orders orders : ordersList) {
				//根据订单查询出所有的订单项
				List<Map<String, Object>> mapList = orderDao.findOrderItemByOid(orders.getOid());
				//遍历集合数据
				for(Map<String, Object> map : mapList) {
					//将集合中的count,subtotal,itemid取出,封装orderItem数据
					OrderItem orderItem = new OrderItem();
					orderItem.setItemid((String)map.get("itemid"));
					orderItem.setCount((Integer)map.get("count"));
					orderItem.setSubtotal((Double)map.get("subtotal"));
					BeanUtils.copyProperties(orderItem, mapList);
					//将集合中的商品Id取出,调用prodDao查询出对应的商品
					Product product = prodDao.findProductById((String)map.get("pid"));
					//将商品对象存入OrderItem中
					orderItem.setProduct(product);
					
					//将订单项存入订单集合中
					orders.getOrderItem().add(orderItem);
				}
			}
			
			//将订单集合返回
			return ordersList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteOrderByOid(String oid) {
		//根据订单ID查询订单项
		List<Map<String, Object>> mapList = orderDao.findOrderItemByOid(oid);
		for(Map<String, Object> map : mapList) {
			//获取每个订单项ID,调用orderDao删除该订单项
			orderDao.deleteOrderItemById((String)map.get("itemid"));
		}
		//再调用orderDao删除订单
		orderDao.deleteOrdersByOid(oid);
	}

}
