package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.ApplyViewBean;
import com.smq.mode2.bean.AskViewBean;
import com.smq.mode2.util.JdbcUtil;

public class ApplyViewDao {

	@SuppressWarnings("unchecked")
	public List<ApplyViewBean> find(String askId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select * from view_apply where askId = ? order by applyTime desc";
		List<ApplyViewBean> list = null;
		try {
			list = (List<ApplyViewBean>) qr.query(query, new BeanListHandler(ApplyViewBean.class), new Object[]{askId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
