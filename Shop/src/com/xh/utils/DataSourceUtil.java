package com.xh.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtil {
	private static DataSource source = new ComboPooledDataSource();
	
	private DataSourceUtil(){}
	
	//��ȡ���ӳ�
	public static DataSource getDataSource() {
		return source;
	}
	
	//��ȡ����
	public static Connection getConnection(){
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeConnection(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				conn = null;
				e.printStackTrace();
			}
		}
	}
}
