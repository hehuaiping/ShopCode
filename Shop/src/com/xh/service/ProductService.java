package com.xh.service;

import java.util.List;

import com.xh.domain.Page;
import com.xh.domain.Product;

public interface ProductService extends Service {
	/**
	 * 最热商品
	 * @return
	 */
	List<Product> findHotProductList();
	/**
	 * 最新商品
	 * @return
	 */
	List<Product> findNewProductList();
	/**
	 * 根据商品类别查找商品
	 * @param cid	商品类别
	 * @param rowPage 
	 * @param thisPage 
	 * @return	返回该类别下的商品集合
	 */
	Page<Product> findProductListByCid(String cid, int thisPage, int rowPage);
	/**
	 * 根据商品ID查询商品
	 * @param pid	商品ID
	 * @return	返回查找到的商品
	 */
	Product findProductById(String pid);

}
