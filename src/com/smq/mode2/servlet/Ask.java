package com.smq.mode2.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.smq.mode2.bean.AskInfoBean;
import com.smq.mode2.bean.AskViewBean;
import com.smq.mode2.dao.AskInfoDao;
import com.smq.mode2.dao.AskViewDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class Ask extends HttpServlet {

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
		String type = request.getParameter("t");
		AskViewDao dao = new AskViewDao();
		if("top".equals(type)){
			int page = Integer.parseInt(request.getParameter("page"));
			List<AskViewBean> beans = dao.findByPage(page);
			if(beans != null){
				bj.type = "success";
				bj.data = new Gson().toJson(beans);
			}
		}else if("my".equals(type)){
			String userId = (String) session.getAttribute("userId");
			List<AskViewBean> beans = dao.findByUser(userId);
			if(beans != null){
				bj.type = "success";
				bj.data = new Gson().toJson(beans);
			}
		}

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
		//添加提问 编辑提问
		String action = request.getParameter("a");
		String info = request.getParameter("info");
		AskInfoBean bean = new Gson().fromJson(info, AskInfoBean.class);
		bean.setUserId(userId);
		if("add".equals(action)){
			String askTime = System.currentTimeMillis() +"";
			bean.setAskTime(askTime);
			AskInfoDao dao = new AskInfoDao();
			if(dao.insert(bean)){
				bj.type = "success";
			}
		}else if("save".equals(action)){
			AskInfoDao dao = new AskInfoDao();
			if(dao.update(bean)){
				bj.type = "success";
			}
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
