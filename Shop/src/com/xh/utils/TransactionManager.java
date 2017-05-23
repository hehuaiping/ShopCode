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
 * �������
 * @author Administrator
 *
 */
public class TransactionManager {
	//˽�й���
	private TransactionManager(){}
	
	//��֤����������ֻ��һ������Դ
	public static DataSource source = new ComboPooledDataSource();
	
	//�����߳�
	//��ʾ�Ƿ���������ı�ʶ
	private static ThreadLocal<Boolean> isTran_local = new ThreadLocal<Boolean>(){
		//��ʼֵ
		protected Boolean initialValue() {
			return false;  //��ʼֵΪfalse,Ĭ�ϲ���������
		};
	};
	
	//������ʵ���ӵĴ������Ӷ���,��������е�close����
	private static ThreadLocal<Connection> proxyConn_local = new ThreadLocal<Connection>();
	//������ʵ����,����֮��ر�����
	private static ThreadLocal<Connection> realConn_local = new ThreadLocal<Connection>();
	
	/**
	 * ��������
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException {
		//ֻҪ���������������,��ʾ��ǰ������Ҫ�����������,�޸ı��Ϊtrue
		isTran_local.set(true);
		//�����ӳ��л�ȡһ������
		final Connection conn = source.getConnection();
		//����ǰ���Ӵ�����ʵ������,Ϊ���ܺ����رո�����
		realConn_local.set(conn);
		//��������
		conn.setAutoCommit(false);
		//������close����
		Connection proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//�����ǰִ�еķ�����close
				if("close".equals(method.getName())) {
					//���ر�����
					return null;
				}
				//ִ��ԭ����
				return method.invoke(conn, args);
			}
		});
		//���������Ӵ��뱾���߳�
		proxyConn_local.set(proxyConn);
	}
	
	/**
	 * ��ȡ����Դ
	 */
	public static DataSource getDataSource() {
		//�жϵ�ǰ�Ƿ���������
		if(isTran_local.get()) {
			//����Source�еĻ�ȡ���ӷ���(getConnection),���ش�������Դ
			return (DataSource) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					//�����ǰ���õ���getConnection()
					if("getConnection".equals(method.getName())) {
						//���ر����߳��п��������������
						return proxyConn_local.get();
					}
					//����ԭ����
					return method.invoke(source, args);
				}
			});
		
		}
		//û�п���������,��������Դ
		return source;
	}
	
	/**
	 * �ύ����
	 */
	public static void commit(){
		//�ύ����
		DbUtils.commitAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * �ع�����
	 */
	public static void rollback() {
		//�ع�����
		DbUtils.rollbackAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * �����߳��б��������,�ͷ���Դ
	 * 
	 */
	public static void clear() {
		//�ر�����
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
