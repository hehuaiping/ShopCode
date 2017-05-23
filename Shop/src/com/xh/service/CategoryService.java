package com.xh.service;

import java.util.List;

import com.xh.domain.Category;

public interface CategoryService extends Service {
	/**
	 * 获取商品类别信息
	 * @return	商品类别信息
	 */
	List<Category> findCategory();

}
