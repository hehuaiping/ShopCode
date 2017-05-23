package com.xh.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.xh.domain.Category;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;
import com.xh.utils.TransactionManager;

public class AdminDaoImpl implements AdminDao {

	public List<Category> findAllCategory() {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addProduct(Product pro) throws SQLException {
		String sql = "insert into product(pid,pname,market_price,shop_price,pimage,is_hot,pdesc,pflag,cid) values(?,?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		runner.update(sql,pro.getPid(),pro.getPname(),pro.getMarket_price(),pro.getShop_price(),pro.getPimage(),pro.getIs_hot(),pro.getPdesc(),pro.getPflag(),pro.getCategory().getCid());
	}

	public List<Orders> findAllOrders() {
		String sql = "select * from orders";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql, new BeanListHandler<Orders>(Orders.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> findOrderItemByOid(String oid) {
		String sql = 	"select pd.pimage,pd.pname,pd.shop_price,oi.count,oi.subtotal "+
						" from orderitem oi,product pd " +
						" where oi.pid=pd.pid and oi.oid=? ";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql, new MapListHandler(),oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User findUserByNaAndPwd(String username, String password) {
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
			String sql = "select * from user where username=? and password=?";
			return runner.query(sql,new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return null;
	}

	public List<Product> findAllProduct() {
		String sql = "select * from product";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql,new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateProduct(Product prod) {
		String sql = "update product set pname=?,is_hot=?,market_price=?,shop_price=?,pimage=?,pnum=?,cid=?,pdesc=? where pid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql, prod.getPname(),prod.getIs_hot(),prod.getMarket_price(),prod.getShop_price(),prod.getPimage(),prod.getPnum(),prod.getCategory().getCid(),prod.getPdesc(),prod.getPid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<String,Object> findProductByPid(String pid) {
		String sql = "select * from product where pid=?";
		QueryRunner runner  = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql, new MapHandler(),pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Category findCategoryByCid(String cid) {
		String sql = "select * from category where cid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			return runner.query(sql, new BeanHandler<Category>(Category.class),cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteCategoryByCid(String cid) {
		String sql = "delete from category where cid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCategory(Category category) {
		String sql = "insert into category values(?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql, category.getCid(),category.getCname());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCategoryByCid(Category category) {
		String sql = "update category set cname=? where cid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql,category.getCname(), category.getCid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<OrderItem> findOrderItemByPid(String pid) {
		String sql = "select * from orderitem where pid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
		  return runner.query(sql,new BeanListHandler<OrderItem>(OrderItem.class),pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteOrderItemByItemId(String itemid) {
		String sql = "delete from orderitem where itemid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql, itemid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteProductByPid(String pid) {
		String sql = "delete from product where pid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
		try {
			runner.update(sql, pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
