package com.xh.dao;

import java.util.List;

import com.xh.domain.Product;

public interface ProductDao extends Dao {
	/**
	 * 查找热门商品
	 * @return
	 */
	List<Product> finfHotProductList();
	/**
	 * 查找最新商品
	 * @return
	 */
	List<Product> finfNewProductList();
	/**
	 * 按照商品类别查找商品
	 * @param cid	商品类别
	 * @return	返回该类商品的集合
	 */
	List<Product> findProductListByCid(String cid,int thisPage,int rowPage);
	/**
	 * 该商品总共有多少条记录
	 * @param cid	商品类别
	 * @return	返回该商品的记录条数
	 */
	int findProductCountByCid(String cid);
	/**
	 * 根据商品ID查询商品
	 * @param pid	商品ID
	 * @return	返回查询到的商品
	 */
	Product findProductById(String pid);
	/**
	 * 扣除商品库存数量
	 * @param pid	商品ID
	 */
	void reductPnum(String pid,int count);

}
