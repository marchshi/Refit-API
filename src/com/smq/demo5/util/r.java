package com.smq.demo5.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.BaseBean;
import com.smq.demo5.entity.BaseEntity;

/**
 * 向response中写入返回数据的类
 * @author shimanqian
 *
 */
public class r {
	
	public static void success(HttpServletResponse response) throws IOException{
		String str = new Gson().toJson(new BaseJson("success","",""));
		response.getWriter().write(str);
		System.out.println(str);
	}
	
	public static void success(HttpServletResponse response,BaseBean bean) throws IOException{
		String json = new GsonBuilder().setExclusionStrategies(new ClassStrategy(bean.getClass()))
				.create().toJson(bean);
		String str =new Gson().toJson(new BaseJson("success","",json));
		response.getWriter().write(str);
		System.out.println(str);
	}
	
	public static void success(HttpServletResponse response,BaseEntity entity) throws IOException{
		String json = new Gson().toJson(entity);
		String str =new Gson().toJson(new BaseJson("success","",json));
		response.getWriter().write(str);
		System.out.println(str);
	}
	
	public static void success(HttpServletResponse response,String data) throws IOException{
		String str =new Gson().toJson(new BaseJson("success","",data));
		response.getWriter().write(str);
		System.out.println(str);
	}
	
	public static void error(HttpServletResponse response ,String content) throws IOException{
		String str = new Gson().toJson(new BaseJson("error",content,""));
		response.getWriter().write(str);
		System.out.println(str);
	}
	
	public static void error(HttpServletResponse response ,String content,String data) throws IOException{
		String str = new Gson().toJson(new BaseJson("error",content,data));
		response.getWriter().write(str);
		System.out.println(str);
	}
}
