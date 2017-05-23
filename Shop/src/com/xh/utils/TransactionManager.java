package com.xh.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 事务管理
 * @author Administrator
 *
 */
public class TransactionManager {
	//私有构造
	private TransactionManager(){}
	
	//保证整个程序中只有一个数据源
	public static DataSource source = new ComboPooledDataSource();
	
	//本地线程
	//表示是否开启过事务的标识
	private static ThreadLocal<Boolean> isTran_local = new ThreadLocal<Boolean>(){
		//初始值
		protected Boolean initialValue() {
			return false;  //初始值为false,默认不开启事务
		};
	};
	
	//保存真实连接的代理连接对象,改造过其中的close方法
	private static ThreadLocal<Connection> proxyConn_local = new ThreadLocal<Connection>();
	//保存真实连接,用完之后关闭连接
	private static ThreadLocal<Connection> realConn_local = new ThreadLocal<Connection>();
	
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException {
		//只要调用了这个方法发,表示当前操作需要开启事务管理,修改标记为true
		isTran_local.set(true);
		//从连接池中获取一个连接
		final Connection conn = source.getConnection();
		//将当前连接存入真实连接中,为了能后续关闭该连接
		realConn_local.set(conn);
		//开启事务
		conn.setAutoCommit(false);
		//改造其close方法
		Connection proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//如果当前执行的方法是close
				if("close".equals(method.getName())) {
					//不关闭连接
					return null;
				}
				//执行原方法
				return method.invoke(conn, args);
			}
		});
		//将代理连接存入本地线程
		proxyConn_local.set(proxyConn);
	}
	
	/**
	 * 获取数据源
	 */
	public static DataSource getDataSource() {
		//判断当前是否开启过事务
		if(isTran_local.get()) {
			//改造Source中的获取连接方法(getConnection),返回代理数据源
			return (DataSource) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					//如果当前调用的是getConnection()
					if("getConnection".equals(method.getName())) {
						//返回本地线程中开启过事务的连接
						return proxyConn_local.get();
					}
					//调用原方法
					return method.invoke(source, args);
				}
			});
		
		}
		//没有开启过事务,返回数据源
		return source;
	}
	
	/**
	 * 提交事务
	 */
	public static void commit(){
		//提交事务
		DbUtils.commitAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollback() {
		//回滚事务
		DbUtils.rollbackAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * 清理线程中保存的数据,释放资源
	 * 
	 */
	public static void clear() {
		//关闭连接
		try {
			realConn_local.get().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		realConn_local.remove();
		isTran_local.remove();
		proxyConn_local.remove();
	}
}
