package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.AskInfoBean;
import com.smq.mode2.util.JdbcUtil;

public class AskInfoDao {
	
	
	public List<AskInfoBean> findByUser(String UserId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from ask_info where userId = ? order by askStatus desc,askTime asc";
		List<AskInfoBean> list = null;
		try {
			list = (List<AskInfoBean>) qr.query(query, new BeanListHandler(AskInfoBean.class), new Object[]{UserId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<AskInfoBean> findByPage(int page){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from ask_info order by askTime asc limit ?,20";
		String index = page*20 +"";
		List<AskInfoBean> list = null;
		try {
			list = (List<AskInfoBean>) qr.query(query, new BeanListHandler(AskInfoBean.class), new Object[]{index});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	public boolean update (AskInfoBean bean){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String update = "update ask_info set askCont=?,refPrice=?,askStatus=? where askId=?";
		int i =0;
		try {
			i = qr.update(update, new Object[]{bean.getAskCont(),bean.getRefPrice(),bean.getAskStatus(),bean.getAskId()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
	public boolean insert(AskInfoBean bean){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String insert = "insert into ask_info(userId,askCont,refPrice,askTime,category,noName,askStatus) values(?,?,?,?,?,?,?)";
		int i =0;
		try {
			i = qr.update(insert, new Object[]{bean.getUserId(),bean.getAskCont(),bean.getRefPrice(),bean.getAskTime(),bean.getCategory(),bean.getNoName(),bean.getAskStatus()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
}
