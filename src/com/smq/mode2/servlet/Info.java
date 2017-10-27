package com.smq.mode2.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.smq.mode2.bean.UserInfoBean;
import com.smq.mode2.dao.UserInfoDao;
import com.smq.mode2.util.BaseJson;
import com.smq.mode2.util.Utils;



public class Info extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Utils.printRequest(request);
		
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		HttpSession session = request.getSession(false);
		if (!Utils.isValid(request) && session==null && !"1".equals(session.getAttribute("isLogin")) ) {
			response.getWriter().write(bj.toString());
			return;
		}
		String action = request.getParameter("a");
		UserInfoDao dao = new UserInfoDao();
		if("get".equals(action)){
			String userId = (String) session.getAttribute("userId");
			UserInfoBean infoBean = dao.find(userId);
			if(infoBean !=null){
				bj.type = "success";
				bj.content = "��ȡ�ɹ�";
				bj.data = new Gson().toJson(infoBean);
			}
		}else if("other".equals(action)){
			String userId = request.getParameter("userId");
			UserInfoBean infoBean = dao.find(userId);
			if(infoBean !=null){
				bj.type = "success";
				bj.content = "��ȡ�ɹ�";
				bj.data = new Gson().toJson(infoBean);
			}
		}
		
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// inputStream ����һ��� �´��ٱ����ͻ����ϴα�����ĩβ���б���
//		Utils.printRequest(request);
		
		response.setContentType("text/html;charset=utf-8");
		BaseJson bj = new BaseJson();
		HttpSession session = request.getSession(false);
		//�ж������Ƿ�Ϸ�
		if (!Utils.isValid(request) && session==null && !"1".equals(session.getAttribute("isLogin")) ) {
			response.getWriter().write(bj.toString());
			return;
		}
		String userId = (String) session.getAttribute("userId");
		String tel = (String) session.getAttribute("tel");
		String action = request.getParameter("a");
		if("save".equals(action)){
			//���ﴫ�ݵ���һ��json���� Ҫ���з����л�
			String info = request.getParameter("info");
			UserInfoBean entity = new Gson().fromJson(info, UserInfoBean.class);
			String name = entity.getName();
			String profession = entity.getProfession();
			String resume = entity.getResume();
			String touxiang  = entity.getTouxiang();
			String city = entity.getCity();
			String sex  = entity.getSex();
			UserInfoDao dao = new UserInfoDao();
			if(dao.isExist(userId)){
				bj.type = dao.update(userId,tel,name,profession,resume,touxiang,city,sex) ? "success" : "fail";
			}else{
				bj.type = dao.insert(userId,tel,name,profession,resume,touxiang,city,sex) ? "success" : "fail";
			}
		}else if("upload".equals(action)){
			//���ϴ����ļ��������� ������һ��URL
			request.getContentType();
			//��uuid��ͼƬ����
			String path = "touxiang/"+UUID.randomUUID()+".jpg";
			String pathDir = request.getRealPath("/")+path;
			System.out.println(request.getContextPath());//  /mode2
			System.out.println(request.getRealPath("/"));//  E:\apache-tomcat-7.0.11\webapps\mode2\
			//this.getClass().getClassLoader().getResource("/").getPath();
			File file = new File(pathDir);
			//������ļ��ַ���
			InputStream is = request.getInputStream();
			//�ļ������
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while( (len = is.read(buffer)) != -1){
				fos.write(buffer, 0, len);
			}
			is.close();
			fos.close();
			bj.type = "success";
			bj.data = "\"" + path + "\"";
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}
	
}
