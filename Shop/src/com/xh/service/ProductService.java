package com.xh.service;

import java.util.List;

import com.xh.domain.Page;
import com.xh.domain.Product;

public interface ProductService extends Service {
	/**
	 * ������Ʒ
	 * @return
	 */
	List<Product> findHotProductList();
	/**
	 * ������Ʒ
	 * @return
	 */
	List<Product> findNewProductList();
	/**
	 * ������Ʒ��������Ʒ
	 * @param cid	��Ʒ���
	 * @param rowPage 
	 * @param thisPage 
	 * @return	���ظ�����µ���Ʒ����
	 */
	Page<Product> findProductListByCid(String cid, int thisPage, int rowPage);
	/**
	 * ������ƷID��ѯ��Ʒ
	 * @param pid	��ƷID
	 * @return	���ز��ҵ�����Ʒ
	 */
	Product findProductById(String pid);

}
