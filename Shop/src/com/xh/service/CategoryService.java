package com.xh.service;

import java.util.List;

import com.xh.domain.Category;

public interface CategoryService extends Service {
	/**
	 * ��ȡ��Ʒ�����Ϣ
	 * @return	��Ʒ�����Ϣ
	 */
	List<Category> findCategory();

}
