package com.xh.dao;

import java.util.List;

import com.xh.domain.Category;

public interface CategoryDao extends Dao {
	/**
	 * ������Ʒ�����Ϣ
	 * @return	��Ʒ�����Ϣ
	 */
	List<Category> findCategory();

}
