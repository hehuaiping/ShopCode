package com.xh.service;

import java.util.List;

import com.xh.dao.CategoryDao;
import com.xh.domain.Category;
import com.xh.factory.BasicFactory;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao dao = BasicFactory.getBasicFactory().getDao(CategoryDao.class);
	public List<Category> findCategory() {
		return dao.findCategory();
	}

}
