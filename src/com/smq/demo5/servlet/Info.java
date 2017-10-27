package com.smq.demo5.servlet;

import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.easemob.EasemobIMUsers;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.Utils;
import com.smq.demo5.util.r;
	
public class Info extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Session session = HibernateUtil.getSession();
//		String userId = request.getParameter("userId");
//		UserInfoBean bean = (UserInfoBean) session.get(UserInfoBean.class, userId);
//		if(bean == null){
//			r.error(response, "用户不存在");
//		}
//		r.success(response, bean);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//保存或编辑个人信息
		UserInfoBean bean = new Gson().fromJson(request.getParameter("bean"),UserInfoBean.class);
		Session session = HibernateUtil.getSession();
		if(session.get(UserInfoBean.class, bean.getUserId()) ==null){
			//第一次登陆
			bean.setInfoDate(System.currentTimeMillis());
//			LoginVerifyBean loginVerify = (LoginVerifyBean) session.get(LoginVerifyBean.class, bean.getUserId());
//			bean.setLoginVerify(loginVerify);
			session.save(bean);
			//创建一个账户信息
			AccountInfoBean account = new AccountInfoBean();
			account.setBalance(0);
			account.setAsset(0);
			account.setExpense(0);
			account.setUser(bean);
			session.save(account);
			r.success(response);
			//第一次登陆要注册一个环信的账号 目前使用官方的swagger 
			//只在服务端进行注册 注册一次获取一次token
			RegisterUsers register = new RegisterUsers();
			User registerUser = new User();
			//TODO 这里默认使用userId作为账户名和密码
			registerUser.setUsername(bean.getUserId()+"");
			registerUser.setPassword(bean.getUserId()+"");
			register.add(registerUser);
			new EasemobIMUsers().createNewIMUserSingle(register);
			
		}else{
			//判断头像是否修改 
			if(bean.getTouxiang()!=null){
				if(Utils.isBase64(bean.getTouxiang())){
					String path = "touxiang/"+UUID.randomUUID()+".jpg";
					Utils.base64ToFile(bean.getTouxiang(), request.getRealPath("/")+path);
					bean.setTouxiang(path);
				}
			}
			session.clear();
			session.update(bean);
			r.success(response,bean);
		}
	}
}
