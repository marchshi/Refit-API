package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Ask extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		Session session = HibernateUtil.getSession();
		AskInfoBean ask = new Gson().fromJson(request.getParameter("bean"),AskInfoBean.class);
		int userId = ask.getUserId();
		session.saveOrUpdate(ask);
		//返回个人的所有提问信息 首页自己在需要刷新的时候进行刷新
		List<AskInfoBean> list = session.createQuery("from AskInfoBean where userId = "+userId + " order by askDate desc").list();
		for(AskInfoBean bean:list){
			Hibernate.initialize(bean.getApplys());
		}
		//只返回提问表和申请表的内容
		FieldStrategy fs = new FieldStrategy();
		fs.setFields(new String[]{"ask","cat","user"});
		String json = new GsonBuilder().setExclusionStrategies(fs)
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list);
		r.success(response, json);
	}
}
