package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.UserLoginBean;
import com.smq.mode2.util.JdbcUtil;

/**
 * 封装一些增删改查的方法
 */
public class UserLoginDao {

	public boolean insert(String userId,String tel,String uuid){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		//将数据插入到登陆记录表中
		String insert = "insert into user_login(userId,tel,uuid,loginDate) value(?,?,?,?)";
		String date = System.currentTimeMillis() + "";
		int i = 0;
		try {
			i = qr.update(insert, new Object[]{userId,tel,uuid,date});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1 ? true : false;
	}
	
	public List<UserLoginBean> find(String userId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String select = "select * from user_login where userId = ?";
		List<UserLoginBean> list = null;
		try {
			list = (List<UserLoginBean>) qr.query(select, new BeanListHandler(UserLoginBean.class),new Object[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
