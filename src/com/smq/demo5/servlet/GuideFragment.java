package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.entity.GuideFragmentEntity;
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class GuideFragment extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//��ȡ����������Ϣ ��Ҫ�Ƕ�GuideFragmentEntity�������
		Session session = HibernateUtil.getSession();
		int userId= (Integer) request.getSession(false).getAttribute("userId");
		//��ȡ���к����йصĶ���
		List<OrderInfoBean> orders = session.createQuery("select ord from OrderInfoBean as ord left join ord.cat as cat where cat.userId=? or ord.payUserId=? order by ord.switch1 desc").setParameter(0, userId).setParameter(1, userId).list();
		//ɸѡ�����ڽ�������Ķ��� �Ƚ���ǰ̨���������ɸѡ������TODO �����ڶ�����ȱһ���ֶ��ж������Ƿ������������
		
		List<GuideFragmentEntity> list = new ArrayList();
		for(OrderInfoBean order:orders){
			GuideFragmentEntity entity = new GuideFragmentEntity();
			entity.setOrderId(order.getOrderId());
			entity.setStatus(order.getStatus());
			entity.setCont(order.isCont());
			if(order.getPayUserId()==userId){
				entity.setType("get");
				entity.setTouxiang(order.getCat().getUser().getTouxiang());
				entity.setName(order.getCat().getUser().getName());
				entity.setChatName(order.getCat().getUser().getUserId()+"");
			}else if(order.getCat().getUserId()==userId){
				entity.setType("to");
				entity.setTouxiang(order.getUser().getTouxiang());
				entity.setName(order.getUser().getName());
				entity.setChatName(order.getUser().getUserId()+"");
			}
			list.add(entity);
		}
		String json = new Gson().toJson(list);
		r.success(response, json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}
