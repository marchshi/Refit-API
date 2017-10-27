package com.smq.mode2.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.smq.mode2.bean.CatInfoBean;
import com.smq.mode2.dao.CatInfoDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class EditCat extends HttpServlet {

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
		String userId = (String) session.getAttribute("userId");
		String action = request.getParameter("a");
		CatInfoDao dao = new CatInfoDao();
		if("get".equals(action)){
			List<CatInfoBean> infoBeans = dao.find(userId);
			String json = new Gson().toJson(infoBeans);
			bj.type = "success";
			bj.data = json;
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
		String action = request.getParameter("a");
		CatInfoDao dao = new CatInfoDao();
		if("save".equals(action)){
			//获取传递的参数
			String json = request.getParameter("info");
			CatInfoBean infoBean = new Gson().fromJson(json, CatInfoBean.class);
			String catId = infoBean.getCatId();
			String category = infoBean.getCategory();
			String label = infoBean.getLabel();
			String catDesc = infoBean.getCatDesc();
			String price = infoBean.getPrice();
			String cont = infoBean.getCont();
			String smodel = infoBean.getSmodel();
			if(catId != null && dao.isExist(catId)){
				if(dao.update(catId,category,label,catDesc,price,cont,smodel)){
					bj.type="success";
				}
			}else{
				if(dao.insert(userId,category,label,catDesc,price,cont,smodel)){
					bj.type="success";
				}
			}
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
