package com.smq.mode2.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.smq.mode2.bean.ApplyViewBean;
import com.smq.mode2.dao.ApplyInfoDao;
import com.smq.mode2.dao.ApplyViewDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class Apply extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utils.printRequest(request);
		
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		HttpSession session = request.getSession(false);
		//判断请求是否合法
		if (!Utils.isValid(request) && session==null && !"1".equals(session.getAttribute("isLogin")) ) {
			response.getWriter().write(bj.toString());
			return;
		}
		String askId = request.getParameter("askId");
		ApplyViewDao dao = new ApplyViewDao();
		List<ApplyViewBean> list = dao.find(askId);
		bj.type = "success";
		bj.data = new Gson().toJson(list);
		
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utils.printRequest(request);
		
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		HttpSession session = request.getSession(false);
		//判断请求是否合法
		if (!Utils.isValid(request) && session==null && !"1".equals(session.getAttribute("isLogin")) ) {
			response.getWriter().write(bj.toString());
			return;
		}
		String userId = (String) session.getAttribute("userId");
		String askId = request.getParameter("askId");
		String catId = request.getParameter("catId");
		ApplyInfoDao dao = new ApplyInfoDao();
		if(dao.insert(askId,userId,catId)){
			bj.type = "success";
		}
		
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
