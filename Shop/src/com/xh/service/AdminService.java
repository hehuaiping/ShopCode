package com.xh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xh.domain.Category;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;

public interface AdminService extends Service {
	/**
	 * 查找商品所有种类
	 * @return
	 */
	List<Category> findAllCategory();
	/**
	 * 添加商品
	 * @throws SQLException 
	 */
	void addProduct(Product product) throws SQLException;
	/**
	 * 查找所有订单
	 * @return
	 */
	List<Orders> findAllOrders();
	/**
	 * 根据订单ID查询该订单下的所有订单项
	 * @param oid	订单ID
	 * @return
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * 根据用户名密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByNaAndPwd(String username, String password);
	/**
	 * 查询所有商品
	 * @return	返回所有商品组成的集合
	 */
	List<Product> findAllProduct();
	/**
	 * 更新商品信息
	 * @param product	封装了最新商品信息的bean
	 */
	void updateProduct(Product product);
	/**
	 * 根据商品ID查询商品
	 * @param pid	商品ID
	 * @return	返回封装了商品信息的集合
	 */
	Map<String, Object> findProductByPid(String pid);
	/**
	 * 根据商品种类ID查询商品种类
	 * @param cid	商品ID
	 * @return	返回该ID商品的种类
	 */
	Category findCategoryByCid(String cid);
	/**
	 * 根据商品种类Id删除商品种类
	 * @param cid	商品种类ID
	 * @return
	 */
	void deleteCategoryByCid(String cid);
	/**
	 * 添加商品种类
	 * @param category 封装了商品种类的数据
	 */
	void addCategory(Category category);
	/**
	 * 更新商品种类数据
	 * @param category 封装了最新商品种类数据的bean
	 */
	void updateCategoryByCid(Category category);
	/**
	 * 根据商品ID删除商品
	 * @param pid	商品ID
	 */
	void deleteProducyByPid(String pid);

}
