package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Account extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		AccountInfoBean bean = (AccountInfoBean) session.createQuery("select account from AccountInfoBean account where account.accountId = ?").setParameter(0, userId).uniqueResult();
		if(bean == null){
			r.error(response, "该账户不存在");
		}else{
			r.success(response, bean);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
}
