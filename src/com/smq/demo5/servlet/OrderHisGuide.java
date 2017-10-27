package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.util.FieldStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class OrderHisGuide extends BaseServlet {
	
	/**
	 * 通过id获得已经完成或者取消的订单 (4已完成 6已取消)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();

		int userId= (Integer) request.getSession(false).getAttribute("userId");
		
		List<OrderInfoBean> orders = session.createQuery("select ord from OrderInfoBean as ord left join ord.cat as cat where cat.userId=? or ord.payUserId=? order by ord.switch1 desc").setParameter(0, userId).setParameter(1, userId).list();

		List<MyOrderEntity> list = new ArrayList();
		for(OrderInfoBean order:orders){
			if(order.getStatus()==4||order.getStatus()==6){
				MyOrderEntity entity = new MyOrderEntity();
				entity.setOrderId(order.getOrderId());
				entity.setOrderDate(order.getSwitch1());
				
				if(order.getPayUserId()==userId){
					entity.setType("get");
					entity.setTouxiang(order.getCat().getUser().getTouxiang());
					entity.setName(order.getCat().getUser().getName());
					entity.setLabel(order.getCat().getLabel());
				}else if(order.getCat().getUserId()==userId){
					entity.setType("to");
					entity.setTouxiang(order.getUser().getTouxiang());
					entity.setName(order.getUser().getName());
				}
				
				entity.setStatus(order.getStatus());
				entity.setPrice(order.getPrice());
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
