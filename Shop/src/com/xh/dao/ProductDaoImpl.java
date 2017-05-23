package com.xh.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xh.domain.Product;
import com.xh.utils.TransactionManager;

public class ProductDaoImpl implements ProductDao {

	public List<Product> finfHotProductList() {
		try {
			String sql = "select * from product where is_hot=? limit ?,?";
			QueryRunner runner  = new QueryRunner(TransactionManager.getDataSource());
			
			return runner.query(sql, new BeanListHandler<Product>(Product.class),1,0,9);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> finfNewProductList() {
		try {
			String sql = "select * from product order by pdate desc limit ?,?";
			QueryRunner runner  = new QueryRunner(TransactionManager.getDataSource());
			
			return runner.query(sql, new BeanListHandler<Product>(Product.class),0,9);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> findProductListByCid(String cid,int thisPage,int rowPage) {
		String sql = "select * from product where cid=? limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return runner.query(sql, new BeanListHandler<Product>(Product.class),cid,thisPage,rowPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int findProductCountByCid(String cid) {
		String sql = "select count(*) from product where cid=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			Long l = (Long)runner.query(sql, new ScalarHandler(),cid);
			return l.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Product findProductById(String pid) {
		String sql = "select * from product where pid=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void reductPnum(String pid,int count) {
		String sql = "update product set pnum=pnum-? where pid=?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			runner.update(sql, count, pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
