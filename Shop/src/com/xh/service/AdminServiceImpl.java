package com.xh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xh.dao.AdminDao;
import com.xh.domain.Category;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;
import com.xh.factory.BasicFactory;

public class AdminServiceImpl implements AdminService {
	AdminDao adminDao = BasicFactory.getBasicFactory().getDao(AdminDao.class);
	
	public List<Category> findAllCategory() {
		return adminDao.findAllCategory();
	}

	public void addProduct(Product product) throws SQLException {
		adminDao.addProduct(product);
	}

	public List<Orders> findAllOrders() {
		return adminDao.findAllOrders();
	}

	public List<Map<String, Object>> findOrderItemByOid(String oid) {
		return adminDao.findOrderItemByOid(oid);
	}

	public User findUserByNaAndPwd(String username, String password) {
		return  adminDao.findUserByNaAndPwd(username,password);
	}

	public List<Product> findAllProduct() {
		return adminDao.findAllProduct();
	}

	public void updateProduct(Product product) {
		adminDao.updateProduct(product);
	}

	public Map<String,Object> findProductByPid(String pid) {
		return adminDao.findProductByPid(pid);
	}

	public Category findCategoryByCid(String cid) {
		return adminDao.findCategoryByCid(cid);
	}

	public void deleteCategoryByCid(String cid) {
		adminDao.deleteCategoryByCid(cid);
	}

	public void addCategory(Category category) {
		adminDao.addCategory(category);
	}

	public void updateCategoryByCid(Category category) {
		adminDao.updateCategoryByCid(category);
	}

	public void deleteProducyByPid(String pid) {
		//1、根据商品ID查询订单项中有关于该商品的记录
		List<OrderItem> orderItemList =  adminDao.findOrderItemByPid(pid);
//		//2、遍历订单项,根据订单项ID获取有关于该订单项订单
//		 adminDao.findOrdersByItemId();
		//3、如果该商品还存在相关的订单项,遍历订单项获取订单项ID,调用adminDao中根据订单项ID删除该订单项的方法
		if(null!=orderItemList) {
			for(OrderItem item : orderItemList) {
				adminDao.deleteOrderItemByItemId(item.getItemid());
			}
		}
		//4、调用adminDao中根据商品ID删除该商品
		adminDao.deleteProductByPid(pid);
	}

}
