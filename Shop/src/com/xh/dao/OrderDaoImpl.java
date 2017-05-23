package com.xh.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.utils.TransactionManager;

public class OrderDaoImpl implements OrderDao {

	public void addOrders(Orders orders) {
		try {
			String sql = "insert into orders(oid,total,state,uid) values(?,?,?,?)";
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			runner.update(sql, orders.getOid(), orders.getTotal(), orders.getState(),orders.getUser().getUid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addOrderItem(OrderItem item) {
		try {
			String sql = "insert into orderitem values(?,?,?,?,?)";
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			runner.update(sql,item.getItemid(),item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrders().getOid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateOrdersAddress(Orders orders) {
		String sql = "update orders set address=?,name=?,telephone=? where oid=?"; 
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql,orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getOid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateState(String r6_Order, int i) {
		String sql = "update orders set state=? where oid=?"; 
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql,i,r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Orders> findOrdersByUid(String uid) {
		String sql = "select * from orders where uid=?"; 
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql,new BeanListHandler<Orders>(Orders.class),uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> findOrderItemByOid(String oid) {
		String sql = "select * from orderitem where oid=?"; 
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql,new MapListHandler(),oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void deleteOrderItemById(String itemid) {
		String sql = "delete from orderitem where itemid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql,itemid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteOrdersByOid(String oid) {
		String sql = "delete from orders where oid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql,oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
