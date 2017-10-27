package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Form extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取类目ID和表单字符串 保存到数据库中
		
		Session session = HibernateUtil.getSession();
		
		int catId = Integer.parseInt(request.getParameter("catId"));
		String form = request.getParameter("form");
		
		session.createQuery("update CatInfoBean cat set cat.form = ? where catId = ?").setParameter(0, form).setParameter(1, catId).executeUpdate();
		
		r.success(response);
		
	}
	
}
