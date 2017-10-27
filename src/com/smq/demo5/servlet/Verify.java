package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.smq.demo5.bean.LoginVerifyBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Verify extends BaseServlet {
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LoginVerifyBean bean = new Gson().fromJson(request.getParameter("bean"),LoginVerifyBean.class);
		//���ﲻ���ֻ��ŵĺ����Խ����ж� �������жϽ���ǰ̨����
		
		Session session = HibernateUtil.getSession();
		
		String verify = "";
		for(int i = 0 ; i<6 ; i++)
			verify += (int)Math.floor(10*Math.random());
		bean.setVerify(verify);
		bean.setFailTimes(0);
		bean.setVerifyDate(System.currentTimeMillis());
		LoginVerifyBean oldBean = (LoginVerifyBean) session.createQuery("from LoginVerifyBean where tel = "+bean.getTel()).uniqueResult();
		//�ж��ֻ����Ƿ����
		if(oldBean == null){
			session.clear();
			session.save(bean);
//			int userId = ((LoginVerifyBean)session.createQuery("from LoginVerifyBean where tel = "+bean.getTel()).uniqueResult()).getUserId();
			int userId = bean.getUserId();
			bean.setUserId(userId);
		}else{
			session.clear();
			long time = bean.getVerifyDate() - oldBean.getVerifyDate();
			if(time < 60000){
				r.error(response, "60����ֻ�ܻ�ȡһ��");
				return;
			}
			bean.setUserId(oldBean.getUserId());
			session.update(bean);
		}
		r.success(response, bean);
	}
}
