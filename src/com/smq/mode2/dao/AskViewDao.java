package com.smq.mode2.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.smq.mode2.bean.AskViewBean;
import com.smq.mode2.util.JdbcUtil;

/**
 * 视图dao仅提供查询功能
 * @author shimanqian
 *
 */
public class AskViewDao {
	
	
	@SuppressWarnings("unchecked")
	public List<AskViewBean> findByUser(String UserId){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String query = "select ask_info.askId,ask_info.userId as userId,name, touxiang,noName,askCont, refPrice,askTime,category,askStatus , count(apply_info.applyId) as applyNum, sum(case when apply_info.applyStatus=2 then 1 else 0 end) as payNum from user_info,ask_info left join apply_info on ask_info.askId=apply_info.askId where ask_info.userId = user_info.userId and ask_info.userId = ? group by ask_info.askId order by askTime desc";
//		String query = "select * from view_ask where userId=? order by askStatus desc,askTime desc";
		List<AskViewBean> list = null;
		try {
			list = (List<AskViewBean>) qr.query(query, new BeanListHandler(AskViewBean.class), new Object[]{UserId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<AskViewBean> findByPage(int page){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		//TODO 视图查找加where判断会导师mysql服务关闭
		String query = "select * from view_ask order by askId desc limit ?,20";
		int index = page*20;
		List<AskViewBean> list = null;
		try {
			list = (List<AskViewBean>) qr.query(query, new BeanListHandler(AskViewBean.class), new Object[]{index});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
