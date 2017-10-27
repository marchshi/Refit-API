package com.smq.mode2.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	
	public static String getVerify(){
		String verify = "";
		for(int i = 0 ; i<6 ; i++)
			verify += (int)Math.floor(10*Math.random());
		return verify;
	}
	public static String getMD5(String str) {
        if (str != null && !str.equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
                }
                str = sb.toString();
            } catch (Exception e) {
            }
        }
        return str;
    }
	
	public static void printRequest(HttpServletRequest request) throws IOException{
		System.out.println("============================");
		//输出请求行 包括请求方法 请求资源 http协议
		System.out.println(request.getMethod()+" "+request.getRequestURL()+" "+request.getRequestURI()+" "+request.getProtocol());
		//输出请求头
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			String line = "";
			String name = names.nextElement();
			line += name + ": ";
			Enumeration<String> headers = request.getHeaders(name);
			while(headers.hasMoreElements()){
				line += headers.nextElement()+"; ";
			}
			System.out.println(line);
		}
		//输出get请求的参数
		System.out.println("---------Parameter---------");
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String name = enumeration.nextElement();
			System.out.println(name+"="+request.getParameter(name));
		}
		//输出body内容
		System.out.println("---------Requset Package---------");
		String body = "";
		InputStream is = request.getInputStream();
		byte[] buffer = new byte[1024];
		int len;
		while((len=is.read(buffer))!=-1){
			body += new String(buffer,0,len);
		}
		System.out.println(body);
		System.out.println("============================");
	}
	
	
	// 接受客户端发来的信息 判断该请求是否合法 每一个请求都必须要对其合法性进行验证 因要封装此方法增加其复用性
	/**
	 * 此方法通过xkey判断请求是否合法
	 * @param request http请求的request对象
	 * @return true表示请求合法 false表示请求不合法
	 */
	public static boolean isValid(HttpServletRequest request) {
		String app = request.getHeader("x-app");
		String uuid = request.getHeader("x-uid");
		String xkey = request.getHeader("x-key");
		String myXkey = Utils.getMD5(uuid + app + "yzdg2017smq^$^");
		return myXkey.equals(xkey) ? true : false;
	}
}
