package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.smq.mode2.bean.UserVerifyBean;
import com.smq.mode2.util.JdbcUtil;


public class UserVerifyDao {
	
	public boolean isExist(String tel){
		if(find(tel)!=null)
			return true;
		return false;
	}
	
	/**
	 * 根据用户的手机号码查询用户的全部验证信息
	 * @param tel 用户的手机号码
	 * @return bean对象
	 */
	public UserVerifyBean find(String tel){
		/**
		 * 1,通过c3p0获取连接池对象
		 * 2,通过DBUtils工具封装JDBC来完成对连接池的操作
		 * 3,使用预编译的方式创建sql语句
		 * 4,将预编译中要插入的数据用object数组包装起来
		 * 5,通过ResultSetHandler接口把结果集封装成不同类型的对象
		 * 	5.1 BeanHandler类： 把结果集的第一行，封装成javabean对象(常用)
		 * 	5.2 BeanListHandler类： 把结果集的每行封装成javabean，把每个javabean放入List集合中（常用）
		 * 	5.3 ScalarHandler类：查询聚合函数(例如:count(*))  (常用)
		 * 6,执行代码 update更新 query查询
		 * TODO DBUtils是否要关闭？
		 */
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from user_verify where tel = ?";
		UserVerifyBean verifyBean = null;
		try {
			verifyBean = (UserVerifyBean)qr.query(query,new BeanHandler(UserVerifyBean.class),new Object[]{tel});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return verifyBean;
	}
	
	public boolean findByLogin(String tel,String uuid,String verify){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select count(*) from user_verify where tel = ? and uuid = ? and verify=?";
		Long count = 0l;
		try {
			count = (Long) qr.query(query, new ScalarHandler(),new Object[]{tel,uuid,verify});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (count == 1) ? true : false;
	}
	
	public boolean insert(String tel,String uuid,String verify){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String insert = "insert into user_verify(tel,uuid,verify,verifyDate) value(?,?,?,?)";
		String date = System.currentTimeMillis() + "";
		int count = 0;
		try {
			count = qr.update(insert,new Object[]{tel,uuid,verify,date});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (count == 1)?true : false;
	}
	
	public boolean update(String tel,String uuid,String verify){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String update = "update user_verify set uuid=?,verify=?,verifyDate=? where tel=?";
		String date = System.currentTimeMillis() + "" ;
		int count = 0;
		try {
			count = qr.update(update,new Object[]{uuid,verify,date,tel});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (count == 1)?true : false;
	}
	
}
