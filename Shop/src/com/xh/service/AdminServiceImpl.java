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
		//1��������ƷID��ѯ���������й��ڸ���Ʒ�ļ�¼
		List<OrderItem> orderItemList =  adminDao.findOrderItemByPid(pid);
//		//2������������,���ݶ�����ID��ȡ�й��ڸö������
//		 adminDao.findOrdersByItemId();
		//3���������Ʒ��������صĶ�����,�����������ȡ������ID,����adminDao�и��ݶ�����IDɾ���ö�����ķ���
		if(null!=orderItemList) {
			for(OrderItem item : orderItemList) {
				adminDao.deleteOrderItemByItemId(item.getItemid());
			}
		}
		//4������adminDao�и�����ƷIDɾ������Ʒ
		adminDao.deleteProductByPid(pid);
	}

}
