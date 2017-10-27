package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class AskList extends BaseServlet {
	
	/**
	 * 返回首页的提问信息 List<AskInfoBean> 其中包含ask info apply表的信息
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		
		List<AskInfoBean> askList = session.createQuery("from AskInfoBean where opening ="+true +" order by askDate desc").list();
		
		FieldStrategy fs = new FieldStrategy();
		fs.setFields(new String[]{"ask","asks","cat","cats","account","orders"});
		String json = new GsonBuilder().setExclusionStrategies(fs)
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(askList);
		
		r.success(response, json);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}
