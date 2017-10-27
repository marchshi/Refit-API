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
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class OrderGetGuide extends BaseServlet {
	
	/**
	 * 通过用户id获取正在帮别人进行导购的订单 (1待处理 2处理中 3确认中 5申诉中)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		
		int userId= (Integer) request.getSession(false).getAttribute("userId");
		
		List<OrderInfoBean> orders = session.createQuery("from OrderInfoBean as ord where ord.payUserId=? order by ord.switch1 desc").setParameter(0, userId).list();
		
		List<MyOrderEntity> list = new ArrayList();
		
		for(OrderInfoBean order:orders){
			if(order.getStatus()==1||order.getStatus()==2||order.getStatus()==3||order.getStatus()==5){
				MyOrderEntity entity = new MyOrderEntity();
				entity.setOrderId(order.getOrderId());
				entity.setOrderDate(order.getSwitch1());
				entity.setType("get");
				entity.setTouxiang(order.getCat().getUser().getTouxiang());
				entity.setName(order.getCat().getUser().getName());
				entity.setStatus(order.getStatus());
				entity.setPrice(order.getPrice());
				entity.setLabel(order.getCat().getLabel());
				entity.setNameId(order.getNameId());
				entity.setCont(order.isCont());
				entity.setServerModel(order.getServerModel());
				entity.setScheTime(order.getScheTime());
				list.add(entity);
			}
		}
		String json = new Gson().toJson(list);
		r.success(response, json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
}
