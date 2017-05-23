package com.xh.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xh.domain.Category;
import com.xh.utils.TransactionManager;

public class CategoryDaoImpl implements CategoryDao {

	public List<Category> findCategory() {
		String sql = "select * from category";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return runner.query(sql, new BeanListHandler<Category>(Category.class));
		}catch(SQLException e ) {
			e.printStackTrace();
		}
		return null;
 	}

}
