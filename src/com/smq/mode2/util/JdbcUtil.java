package com.smq.mode2.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 提供c3p0连接池的工具类 连接数据库都使用这个工具类
 * @author shimanqian
 *
 */
public class JdbcUtil {
	//一个数据库只需要一个连接池对象
	private static DataSource ds = new ComboPooledDataSource();
	
	/**
	 * 获取连接池对象的方法
	 */
	public static DataSource getDataSource(){
		return ds;
	}
	/**
	 * 获取连接对象的方法
	 */
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
