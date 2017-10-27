package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.ApplyViewBean;
import com.smq.mode2.bean.CatViewBean;
import com.smq.mode2.util.JdbcUtil;

public class CatViewDao {
	
	public List<CatViewBean> search(String text){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String select = "select user_info.userId,name,touxiang,catId,category,label,catDesc,price,stimes from cat_info,user_info where cat_info.userId = user_info.userId and category like '%"+text+"%'";
		List list = new ArrayList<CatViewBean>();
		try {
			list = (List) qr.query(select,new BeanListHandler(CatViewBean.class), new Object[]{});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
