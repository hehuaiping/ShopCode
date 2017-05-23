package com.xh.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xh.domain.Category;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;

public interface AdminDao extends Dao {
	/**
	 * 查找商品分类数据
	 * @return
	 */
	List<Category> findAllCategory();
	/**
	 * 添加商品
	 * @param product
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
	 * @return	返回该订单下的所有订单项
	 */
	List<Map<String, Object>> findOrderItemByOid(String oid);
	/**
	 * 根据用户名密码查询用户
	 * @param username	用户名
	 * @param password	密码
	 * @return	返回查找到的用户
	 */
	User findUserByNaAndPwd(String username, String password);
	/**
	 * 查询所有商品
	 * @return	返回所有商品集合
	 */
	List<Product> findAllProduct();
	/**
	 * 更新商品信息
	 * @param product 封装了最新商品数据的bean
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
	 * @param cid	商品种类ID
	 * @return	返回该ID的商品种类
	 */
	Category findCategoryByCid(String cid);
	/**
	 * 根据商品种类ID删除商品
	 * @param cid
	 */
	void deleteCategoryByCid(String cid);
	/**
	 * 添加商品种类
	 * @param category 封装了商品种类的实体
	 */
	void addCategory(Category category);
	/**
	 * 更新商品种类数据
	 * @param category 封装了最新商品种类的数据的bean
	 */
	void updateCategoryByCid(Category category);
	/**
	 * 根据商品ID查询有关当前商品的所有订单项
	 * @param pid 商品ID
	 * @return	返回关于当前商品的所有订单项
	 */
	List<OrderItem> findOrderItemByPid(String pid);
	/**
	 * 根据订单项Id,删除该订单项
	 * @param itemid 订单项Id
	 */
	void deleteOrderItemByItemId(String itemid);
	/**
	 * 根据商品ID删除该商品
	 * @param pid	商品ID
	 */
	void deleteProductByPid(String pid);

}
