package com.smq.demo5.servlet;

import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.LoginHisBean;
import com.smq.demo5.bean.LoginVerifyBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.easemob.EasemobAuthToken;
import com.smq.demo5.easemob.EasemobIMUsers;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Login extends BaseServlet {
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LoginVerifyBean bean = new Gson().fromJson(request.getParameter("bean"),LoginVerifyBean.class);
		
		Session session = HibernateUtil.getSession();
//		session.createQuery("from LoginVerifyBean where tel = "+bean.getTel()+" and verify = "+bean.getVerify() +"and uuid="+bean.getUuid()).uniqueResult();
		LoginVerifyBean oldBean = (LoginVerifyBean) session.get(LoginVerifyBean.class, bean.getUserId());
		//判断用户ID或手机号是否存在
		if(oldBean == null || !bean.getTel().equals(oldBean.getTel())){
			r.error(response, "该用户不存在");
			return;
		}
		//判断uuid和验证码是否正确
		if( oldBean.getUuid().equals(bean.getUuid()) && oldBean.getVerify().equals(bean.getVerify())){
			//正确 判断是否第一次登陆
//			UserInfoBean user = (UserInfoBean) session.get(UserInfoBean.class, bean.getUserId());
			UserInfoBean user = (UserInfoBean) session.createQuery("from UserInfoBean where userId = ?").setParameter(0, bean.getUserId()).uniqueResult();
			if(user ==null){
				r.error(response, "first");
				return;
			}
			//登录前将对应的环信账号下线
			new EasemobIMUsers().disconnectIMUser(bean.getUserId()+"");
			
			//保存登陆信息
			LoginHisBean his = new LoginHisBean();
			his.setUserId(bean.getUserId());
			his.setTel(bean.getTel());
			his.setUuid(bean.getUuid());
			his.setLoginDate(System.currentTimeMillis());
			String loginer = request.getParameter("loginer");
			his.setLoginer(Integer.parseInt(loginer));
			session.save(his);
			//获取有关用户的所有的数据并返回 info表 cat表 ask表 
			FieldStrategy fs = new FieldStrategy();
			fs.setFields(new String[]{"user","cat","ask","order","account"});
			Gson gson = new GsonBuilder().setExclusionStrategies(fs)
					.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
			r.success(response,gson.toJson(user));
			//将登陆信息保存在会话中
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("isLogin", "1");
			httpSession.setAttribute("userId", user.getUserId());
		}else{
			//失败超过五次
			if(oldBean.getFailTimes() >=4){
				r.error(response, "多次错误，请重新获取验证码");
				return;
			}
			oldBean.setFailTimes(oldBean.getFailTimes()+1);
			session.update(oldBean);
			r.error(response, "验证码错误");
		}
	}

}
