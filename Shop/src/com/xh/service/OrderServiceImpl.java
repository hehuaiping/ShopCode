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
		//1������orderDao����Ӷ����ķ���
		orderDao.addOrders(orders);
		//2������orderDao����Ӷ�����ķ���,�������1�ζ��
		//��ȡ������
		List<OrderItem> orderList = orders.getOrderItem();
		for(OrderItem item : orderList) {
			//����orderDao����Ӷ�����ķ���
			orderDao.addOrderItem(item);
			//3������Ʒ���п۳��������
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
			//��װorders
			//1�����ݵ�ǰ�û�ID��ѯ�����ж���
			List<Orders> ordersList = orderDao.findOrdersByUid(uid);
			//2������orders���ѯ�����ж������
			for(Orders orders : ordersList) {
				//���ݶ�����ѯ�����еĶ�����
				List<Map<String, Object>> mapList = orderDao.findOrderItemByOid(orders.getOid());
				//������������
				for(Map<String, Object> map : mapList) {
					//�������е�count,subtotal,itemidȡ��,��װorderItem����
					OrderItem orderItem = new OrderItem();
					orderItem.setItemid((String)map.get("itemid"));
					orderItem.setCount((Integer)map.get("count"));
					orderItem.setSubtotal((Double)map.get("subtotal"));
					BeanUtils.copyProperties(orderItem, mapList);
					//�������е���ƷIdȡ��,����prodDao��ѯ����Ӧ����Ʒ
					Product product = prodDao.findProductById((String)map.get("pid"));
					//����Ʒ�������OrderItem��
					orderItem.setProduct(product);
					
					//����������붩��������
					orders.getOrderItem().add(orderItem);
				}
			}
			
			//���������Ϸ���
			return ordersList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteOrderByOid(String oid) {
		//���ݶ���ID��ѯ������
		List<Map<String, Object>> mapList = orderDao.findOrderItemByOid(oid);
		for(Map<String, Object> map : mapList) {
			//��ȡÿ��������ID,����orderDaoɾ���ö�����
			orderDao.deleteOrderItemById((String)map.get("itemid"));
		}
		//�ٵ���orderDaoɾ������
		orderDao.deleteOrdersByOid(oid);
	}

}
