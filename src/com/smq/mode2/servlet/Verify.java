package com.smq.mode2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smq.mode2.dao.UserVerifyDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class Verify extends HttpServlet {

	/**
	 * 客户端向服务器发送获取验证码的请求 服务器要判断该请求是否合法
	 * 其中包括 1，是不是APP端发送过来的 x-key 2，请求间隔有没有1分钟
	 * 请求合法则通知短信服务器向客户端发送一条短信(由http响应代替) 
	 * 同时要将用户的请求信息(tel,uuid,verify等)存入数据库中 以供登录时验证 
	 * 这里不需要建立会话 降低耦合性 
	 * servlet为控制器处理业务逻辑 dao连接数据库 
	 * 一个servlet对应一个请求 一个dao对应一个表
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
		UserVerifyDao dao = new UserVerifyDao();
		String tel = request.getParameter("tel");
		String uuid = request.getHeader("x-uid");
		String verify = Utils.getVerify();
		if(!dao.isExist(tel)){
			//注册
			if(dao.insert(tel,uuid,verify)){
				bj.type = "success";
				bj.content = "注册成功";
				bj.data = "\""+verify+"\"";
			}
		}else{
			String vDate = dao.find(tel).getVerifyDate();
			long time = System.currentTimeMillis() - Long.parseLong(vDate);
			if (time < 60000) {
				bj.type = "fail";
				bj.content = "60秒内只能注册一次";
			}else{
				if(dao.update(tel,uuid,verify)){
					bj.type = "success";
					bj.content = "获取验证码成功";
					bj.data = "\""+verify+"\"";
				}
			}
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
