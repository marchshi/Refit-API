package com.smq.mode2.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.smq.mode2.bean.UserInfoBean;
import com.smq.mode2.util.JdbcUtil;

public class UserInfoDao {
	
	public boolean isExist(String user_id){
		if(find(user_id)!=null)
			return true;
		return false;
	}
	
	public UserInfoBean find(String userId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String select = "select * from user_info where userId = ?";
		UserInfoBean infoBean = null;
		try {
			infoBean = (UserInfoBean) qr.query(select, new BeanHandler(UserInfoBean.class), new Object[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoBean;
	}
	
	public boolean update(String userId,String tel,String name,String profession,String resume,String touxiang,String city,String sex){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String update = "update user_info set tel=?,name=?,profession=?,resume=?,touxiang=?,city=?,sex=?,infoDate=? where userId=?";
		String date = System.currentTimeMillis() + "";
		int i = 0;
		try {
			i = qr.update(update,new Object[]{tel,name,profession,resume,touxiang,city,sex,date,userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1 ? true : false;
	}
	
	public boolean insert(String userId,String tel,String name,String profession,String resume,String touxiang,String city,String sex){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String insert= "insert into user_info values(?,?,?,?,?,?,?,?,?)";
		String date = System.currentTimeMillis() + "";
		int i = 0;
		try {
			i = qr.update(insert,new Object[]{userId,tel,name,profession,resume,touxiang,city,sex,date});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1 ? true : false;
	}
	
}
