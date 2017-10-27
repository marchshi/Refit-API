package com.smq.mode2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smq.mode2.dao.CatInfoDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class SaveForm extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		CatInfoDao dao = new CatInfoDao();
		String catId = request.getParameter("catId");
		String form = request.getParameter("form");
		if(dao.saveForm(catId,form)){
			bj.type = "success";
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}
}
