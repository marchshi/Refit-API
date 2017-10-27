package com.smq.mode2.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.smq.mode2.bean.CatTableBean;
import com.smq.mode2.dao.CatTableDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;

public class CatTable extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utils.printRequest(request);
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		//�ж������Ƿ�Ϸ�
		/*if (!Utils.isValid(request)) {
			response.getWriter().write(bj.toString());
			return;
		}*/
		String path = this.getClass().getClassLoader().getResource("/").getPath() + "category1.xml";
		System.out.println(path);
		CatTableDao dao = new CatTableDao();
		List<CatTableBean> list = dao.getTable(path);
		String data = new Gson().toJson(list);
		bj.data = data;
		bj.type = "success";
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
