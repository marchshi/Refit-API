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
	 * �ͻ�������������ͻ�ȡ��֤������� ������Ҫ�жϸ������Ƿ�Ϸ�
	 * ���а��� 1���ǲ���APP�˷��͹����� x-key 2����������û��1����
	 * ����Ϸ���֪ͨ���ŷ�������ͻ��˷���һ������(��http��Ӧ����) 
	 * ͬʱҪ���û���������Ϣ(tel,uuid,verify��)�������ݿ��� �Թ���¼ʱ��֤ 
	 * ���ﲻ��Ҫ�����Ự ��������� 
	 * servletΪ����������ҵ���߼� dao�������ݿ� 
	 * һ��servlet��Ӧһ������ һ��dao��Ӧһ����
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
			//ע��
			if(dao.insert(tel,uuid,verify)){
				bj.type = "success";
				bj.content = "ע��ɹ�";
				bj.data = "\""+verify+"\"";
			}
		}else{
			String vDate = dao.find(tel).getVerifyDate();
			long time = System.currentTimeMillis() - Long.parseLong(vDate);
			if (time < 60000) {
				bj.type = "fail";
				bj.content = "60����ֻ��ע��һ��";
			}else{
				if(dao.update(tel,uuid,verify)){
					bj.type = "success";
					bj.content = "��ȡ��֤��ɹ�";
					bj.data = "\""+verify+"\"";
				}
			}
		}
		System.out.println(bj.toString());
		response.getWriter().write(bj.toString());
	}

}
