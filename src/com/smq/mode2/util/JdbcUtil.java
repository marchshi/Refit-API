package com.smq.mode2.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * �ṩc3p0���ӳصĹ����� �������ݿⶼʹ�����������
 * @author shimanqian
 *
 */
public class JdbcUtil {
	//һ�����ݿ�ֻ��Ҫһ�����ӳض���
	private static DataSource ds = new ComboPooledDataSource();
	
	/**
	 * ��ȡ���ӳض���ķ���
	 */
	public static DataSource getDataSource(){
		return ds;
	}
	/**
	 * ��ȡ���Ӷ���ķ���
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
