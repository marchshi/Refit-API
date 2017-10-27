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
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Apply extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		int askId = Integer.parseInt(request.getParameter("askId"));
		List<ApplyInfoBean> list = session.createQuery("from ApplyInfoBean where askId = " + askId +" order by applyDate desc").list();
		for(ApplyInfoBean info:list){
			CatInfoBean cat = (CatInfoBean) session.get(CatInfoBean.class, info.getCatId());
			info.setCat(cat);
		}
		//返回申请表 类目表和用户信息表
		FieldStrategy fs = new FieldStrategy();
		fs.setFields(new String[]{"cats","ask","orders","applys","asks","logins","account","loginVerify"});
		String json = new GsonBuilder().setExclusionStrategies(fs)
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list);
		r.success(response, json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		ApplyInfoBean apply = new Gson().fromJson(request.getParameter("bean"), ApplyInfoBean.class);
		int askId = apply.getAskId();
		session.save(apply);
		
		List<ApplyInfoBean> list = session.createQuery("from ApplyInfoBean where askId = " + askId +" order by applyDate desc").list();
		//返回申请表 类目表和用户信息表
		for(ApplyInfoBean info:list){
			CatInfoBean cat = (CatInfoBean) session.get(CatInfoBean.class, info.getCatId());
			info.setCat(cat);
		}
		FieldStrategy fs = new FieldStrategy();
		fs.setFields(new String[]{"cats","ask","orders","applys","asks","logins","account","loginVerify"});
		String json = new GsonBuilder().setExclusionStrategies(fs)
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list);
		r.success(response, json);
		
	}
	
}
