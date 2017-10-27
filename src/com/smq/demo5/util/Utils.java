package com.smq.demo5.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import Decoder.BASE64Decoder;

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
	
	/**
	 * �������а�io��������ʹ�ø÷���
	 * @param request
	 * @throws IOException
	 */
	public static void printRequest(HttpServletRequest request) throws IOException{
		System.out.println("============================");
		//��������� �������󷽷� ������Դ httpЭ��
		System.out.println(request.getMethod()+" "+request.getRequestURL()+" "+request.getRequestURI()+" "+request.getProtocol());
		//�������ͷ
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
		//���get����Ĳ���
		System.out.println("---------Parameter---------");
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String name = enumeration.nextElement();
			System.out.println(name+"="+request.getParameter(name));
		}
		//���body����
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
	
	
	// ���ܿͻ��˷�������Ϣ �жϸ������Ƿ�Ϸ� ÿһ�����󶼱���Ҫ����Ϸ��Խ�����֤ ��Ҫ��װ�˷��������临����
	/**
	 * �˷���ͨ��xkey�ж������Ƿ�Ϸ�
	 * @param request http�����request����
	 * @return true��ʾ����Ϸ� false��ʾ���󲻺Ϸ�
	 */
	public static boolean isValid(HttpServletRequest request) {
		String app = request.getHeader("x-app");
		String uuid = request.getHeader("x-uid");
		String xkey = request.getHeader("x-key");
		String myXkey = Utils.getMD5(uuid + app + "yzdg2017smq^$^");
		return myXkey.equals(xkey) ? true : false;
	}
	
	public static boolean base64ToFile(String base64,String path){
		if(base64 == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//����
			byte[] b = decoder.decodeBuffer(base64);
			for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
							b[i] += 256;
					}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * �ж��ַ���url����base64?
	 * @param str
	 * @return
	 */
	public static boolean isBase64(String str){
		if(str.matches("touxiang/(.*).jpg")){
			return false;
		}
		return true;
	}
	
}
