package com.xh.dao;

import java.util.List;

import com.xh.domain.Product;

public interface ProductDao extends Dao {
	/**
	 * ����������Ʒ
	 * @return
	 */
	List<Product> finfHotProductList();
	/**
	 * ����������Ʒ
	 * @return
	 */
	List<Product> finfNewProductList();
	/**
	 * ������Ʒ��������Ʒ
	 * @param cid	��Ʒ���
	 * @return	���ظ�����Ʒ�ļ���
	 */
	List<Product> findProductListByCid(String cid,int thisPage,int rowPage);
	/**
	 * ����Ʒ�ܹ��ж�������¼
	 * @param cid	��Ʒ���
	 * @return	���ظ���Ʒ�ļ�¼����
	 */
	int findProductCountByCid(String cid);
	/**
	 * ������ƷID��ѯ��Ʒ
	 * @param pid	��ƷID
	 * @return	���ز�ѯ������Ʒ
	 */
	Product findProductById(String pid);
	/**
	 * �۳���Ʒ�������
	 * @param pid	��ƷID
	 */
	void reductPnum(String pid,int count);

}
