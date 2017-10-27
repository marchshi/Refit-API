package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Search extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		String text = new String(request.getParameter("text").getBytes("iso-8859-1"),"utf-8");
		System.out.println(text);
		//TODO 搜索排序的模式
		int sortMode = Integer.parseInt(request.getParameter("sortMode"));
		
		List<CatInfoBean> cats = new ArrayList<CatInfoBean>();
		
		List<CatNameBean> nameList = session.createQuery("from CatNameBean where childName like ?").setParameter(0, "%"+text+"%").list();
		
		for(CatNameBean nameId:nameList){
			List<CatInfoBean> list = session.createQuery("from CatInfoBean where nameId = ?").setParameter(0, nameId.getNameId()).list();
			for(CatInfoBean item:list){
				CatInfoBean cat = (CatInfoBean) session.get(CatInfoBean.class, item.getCatId());
				cats.add(cat);
			}
		}
		//包含 cat user的信息
		FieldStrategy fs = new FieldStrategy();
		fs.setFields(new String[]{"cats","asks","applys","orders","logins","account","loginVerify"});
		String json = new GsonBuilder().setExclusionStrategies(fs)
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(cats);
		r.success(response, json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
}
