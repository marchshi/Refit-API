package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.smq.demo5.util.BaseJson;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.Utils;
import com.smq.demo5.util.r;

public class BaseServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(!Utils.isValid(request)){
			r.error(response, "���󲻺Ϸ�");
			return;
		}
		
		Utils.printRequest(request);
		response.setContentType("text/html;charset=utf-8");
		
		Transaction tx = null;
		try {
			Session session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			super.service(request, response);
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();// �����쳣���ع�
			}
			e.printStackTrace();
		} finally {
			//4. �ύ����
			if(tx!=null){
				tx.commit();  // �ύ���Զ��ر�session
			}
		}
		
	}
}
