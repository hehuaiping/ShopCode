package com.xh.dao;

import java.util.List;

import com.xh.domain.Category;

public interface CategoryDao extends Dao {
	/**
	 * 查找商品类别信息
	 * @return	商品类别信息
	 */
	List<Category> findCategory();

}
