package com.smq.mode2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smq.mode2.dao.UserLoginDao;
import com.smq.mode2.dao.UserVerifyDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class Login extends HttpServlet {
	
	/**
	 * login作为程序的入口 要通过会话保留登陆信息
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utils.printRequest(request);
		
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		if (!Utils.isValid(request) ) {
			response.getWriter().write(bj.toString());
			return;
		}
		String tel = request.getParameter("tel");
		String verify = request.getParameter("verify");
		String uuid = request.getHeader("x-uid");
		HttpSession session = request.getSession(true);
		UserVerifyDao verifyDao = new UserVerifyDao();
		//用于判断登陆是否成功
		if(verifyDao.findByLogin(tel, uuid, verify)){
			String userId = verifyDao.find(tel).getUserId();
			UserLoginDao loginDao = new UserLoginDao();
			//首先判断是否第一次登陆
			if(loginDao.find(userId).isEmpty()){
				bj.data = "firstLogin";
			}
			if(loginDao.insert(userId,tel,uuid)){
				session.setAttribute("isLogin", "1");
				session.setAttribute("userId", userId);
				session.setAttribute("tel", tel);
				bj.type = "success";
				bj.content = new String("登陆成功");
			}
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
