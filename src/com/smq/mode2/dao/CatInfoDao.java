package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.smq.mode2.bean.CatInfoBean;
import com.smq.mode2.util.JdbcUtil;

public class CatInfoDao {
	
	public boolean isExist(String catId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select count(*) from cat_info where catId = ?";
		long i = 0;
		try {
			i = (Long) qr.query(query, new ScalarHandler(),new Object[]{catId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
	public boolean insert (String userId,String category,String label,String catDesc,String price,String cont,String smodel){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		//插入的是类目的基本信息
		String insert = "insert into cat_info(userId,category,label,catDesc,price,cont,smodel) value(?,?,?,?,?,?,?)";
		int i = 0;
		try {
			i = qr.update(insert,new Object[]{userId,category,label,catDesc,price,cont,smodel});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
	public boolean update (String catId,String category,String label,String catDesc,String price,String cont,String smodel){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		//插入的是类目的基本信息
		String insert = "update cat_info set category=?,label=?,catDesc=?,price=?,cont=?,smodel=? where catId=?";
		int i = 0;
		try {
			i = qr.update(insert,new Object[]{category,label,catDesc,price,cont,smodel,catId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
	public List<CatInfoBean> find(String userId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from cat_info where userId = ?";
		List<CatInfoBean> infoBeans = null;
		try {
			infoBeans = (List<CatInfoBean>) qr.query(query, new BeanListHandler(CatInfoBean.class),new Object[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoBeans;
	}
	
	public boolean saveForm(String catId,String form){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String update = "update cat_info set form = ? where catId = ?";
		int count = 0;
		try {
			count = qr.update(update,new Object[]{form,catId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count == 1? true:false;
	}
	
}
