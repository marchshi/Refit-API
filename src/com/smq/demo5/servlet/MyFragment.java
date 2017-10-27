package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.entity.MyFragmentEntity;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class MyFragment extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		
		HttpSession httpSession = request.getSession(false);
		
		int userId = (Integer) httpSession.getAttribute("userId");
		
		UserInfoBean info = (UserInfoBean) session.get(UserInfoBean.class, userId);
		
		MyFragmentEntity entity = new MyFragmentEntity();
		
		entity.setTouxiang(info.getTouxiang());
		entity.setName(info.getName());
		entity.setProfession(info.getProfession());
		int serverTimes = 0;
		long serverIncome = 0;
		for(CatInfoBean cat:info.getCats()){
			for(OrderInfoBean order:cat.getOrders()){
				if(order.getStatus()==4){
					serverTimes++;
					serverIncome += order.getPrice();
				}
			}
		}
		entity.setServerTimes(serverTimes);
		entity.setServerIncome(serverIncome);
		entity.setBalance(info.getAccount().getBalance());
		entity.setTel(info.getTel());
		r.success(response, entity);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}
