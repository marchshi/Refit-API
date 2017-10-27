package com.smq.demo5.servlet;

import io.swagger.client.model.Msg;
import io.swagger.client.model.UserName;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smq.demo5.bean.AccountCompanyBean;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.AccountRecordBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.easemob.EasemobSendMessage;
import com.smq.demo5.util.CheckAccountUtil;
import com.smq.demo5.util.ClassStrategy;
import com.smq.demo5.util.HibernateProxyTypeAdapter;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Order extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		if(userId !=0){
			//这里使用别名 order就会一直报错 不知道为什么
			List<OrderInfoBean> list = session.createQuery("from OrderInfoBean as ord left join ord.cat as cat where cat.userId = ?").setParameter(0, userId).list();
			
			String json = new GsonBuilder().setExclusionStrategies(new ClassStrategy(OrderInfoBean.class))
					.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list);
			
			r.success(response, json);
			return;
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 目标：生成一个订单信息
		 * 1 查看账户里面的钱是否足够
		 * 2 个人账户扣钱 
		 * 3 生成订单
		 * 4 官方账户加钱 
		 * 5 保留个人账户变动记录
		 * 6 判断个人账户和官方账户是否安全
		 */
		Session session = HibernateUtil.getSession();
		
		OrderInfoBean order = new Gson().fromJson(request.getParameter("bean"), OrderInfoBean.class);
		
		int pauUserId = order.getPayUserId();
		AccountInfoBean account = (AccountInfoBean) session.get(AccountInfoBean.class, pauUserId);
		if(account.getBalance() >= order.getPrice()){
			account.setBalance(account.getBalance()-order.getPrice());
			account.setExpense(account.getExpense()+order.getPrice());
			
			
			//TODO 订单来源尚未设置
			order.setOtderFrom(1);
			order.setStatus(1);
			order.setSwitch1(System.currentTimeMillis());
			session.save(order);
			
			AccountCompanyBean company = (AccountCompanyBean) session.createQuery("from AccountCompanyBean order by recordId desc").setFirstResult(0).setMaxResults(1).uniqueResult();
			session.evict(company);
			company.setBalance(company.getBalance()+order.getPrice());
			company.setIncome(company.getIncome()+order.getPrice());
			company.setRecordDate(System.currentTimeMillis());
			company.setStatus(2);
			company.setMoney(order.getPrice());
			company.setAboutId(order.getOrderId());
			session.save(company);
			
			AccountRecordBean record = new AccountRecordBean();
			record.setAccountId(pauUserId);
			record.setStatus(2);
			record.setOrderId(order.getOrderId());
			CatInfoBean cat = (CatInfoBean) session.get(CatInfoBean.class, order.getCatId());
			record.setPayAccountId(cat.getUserId());
			record.setPrice(order.getPrice());
			record.setPayDate(System.currentTimeMillis());
			session.save(record);
			
			if(CheckAccountUtil.check(session, account)&&CheckAccountUtil.checkCompany(company)){
				r.success(response);
			}else{
				throw new ServletException();
			}
			
			//订单建立成功后 向环信中发送一条信息
//			Msg msg = new Msg();
//			msg.from(order.getCat().getUserId()+"");
//			UserName userName = new UserName();
//			userName.add(order.getPayUserId()+"");
//			msg.setTarget(userName);
//			msg.setMsg("开始导购");
//			msg.setTargetType("users");
//			new EasemobSendMessage().sendMessage(msg);
			
		}else{
			r.error(response, "账户余额不足");
		}
		
	}
	
}




