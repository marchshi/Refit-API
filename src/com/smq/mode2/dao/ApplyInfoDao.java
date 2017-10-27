package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.ApplyInfoBean;
import com.smq.mode2.util.JdbcUtil;

public class ApplyInfoDao {
	
	/**
	 * 不能重复申请和申请自己提问的问题
	 * @param askId
	 * @param catId
	 * @return
	 */
	/*public boolean isApplyed(String askId,String catId){
		List<ApplyInfoBean> list = find(askId);
		for(ApplyInfoBean bean : list){
			bean.get
		}
	}*/
	
	public List<ApplyInfoBean> find(String askId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from apply_info where askId = ?";
		List<ApplyInfoBean> list = null;
		try {
			list = (List<ApplyInfoBean>) qr.query(query, new BeanListHandler(ApplyInfoBean.class),new Object[]{askId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean insert(String askId,String userId,String catId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String insert = "insert into apply_info(askId,userId,catId,applyTime) value(?,?,?,?)";
		String applyTime = System.currentTimeMillis() +"";
		int i = 0;
		try {
			i = qr.update(insert,new Object[]{askId,userId,catId,applyTime});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i==1?true:false;
	}
	
}
