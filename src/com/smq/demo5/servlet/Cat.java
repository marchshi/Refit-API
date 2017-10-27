package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.util.ClassStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.Utils;
import com.smq.demo5.util.r;

public class Cat extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CatInfoBean bean = new Gson().fromJson(request.getParameter("bean"), CatInfoBean.class);
		if(bean == null){
			r.error(response, "");
			return;
		}
		if(bean.getForm() == null){
			String str = new String("[{\"id\":1,\"initLine\":2,\"options\":[],\"title\":\"简要说明您的主要需求？\",\"type\":3},{\"id\":2,\"initLine\":2,\"options\":[],\"title\":\"其他补充说明\",\"type\":3}]".getBytes("utf-8"),"utf-8");
			bean.setForm(str);
		}
		Session session = HibernateUtil.getSession();
		session.saveOrUpdate(bean);
		List<CatInfoBean> list = session.createQuery("from CatInfoBean where userId ="+bean.getUserId()).list();
		String json = new GsonBuilder().setExclusionStrategies(new ClassStrategy(CatInfoBean.class))
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list);
		r.success(response,json);
	}
}
