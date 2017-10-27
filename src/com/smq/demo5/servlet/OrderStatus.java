package com.smq.demo5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.smq.demo5.bean.AccountCompanyBean;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.AccountRecordBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.util.CheckAccountUtil;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class OrderStatus extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1 判断订单状态的修改者是用户还是导购员
		//2 对订单的状态进行修改
		
		Session session = HibernateUtil.getSession();
		
		int userId = (Integer) request.getSession(false).getAttribute("userId");
		
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		int status = Integer.parseInt(request.getParameter("status"));
		
		int source = -1;
		
		OrderInfoBean order = (OrderInfoBean) session.get(OrderInfoBean.class, orderId);
		
		if(order.getPayUserId() == userId){
			source = 0;
		}else if(order.getCat().getUserId() == userId){
			source = 1;
		}else{
			throw new ServletException();
		}
		
		switch(status){
			case 1:
				
				break;
			case 2:
				if(source==1){
					order.setStatus(2);
					order.setSwitch2(System.currentTimeMillis());
				}else if(order.getStatus()!=1){
					order.setStatus(2);
				}
				break;
			case 3:
				if(source==1){
					order.setStatus(3);
					order.setSwitch3(System.currentTimeMillis());
				}
				break;
			case 4:
				if(source==0){
					order.setStatus(4);
					order.setSwitch4(System.currentTimeMillis());
					//订单完成 转钱！
					//1 官方账户表发生变动
					//2 充值体现表发生变动
					//3 个人账户发生变动
					//4 检验安全性
					AccountCompanyBean company = (AccountCompanyBean) session.createQuery("from AccountCompanyBean order by recordId desc").setFirstResult(0).setMaxResults(1).uniqueResult();
					if(company.getBalance()<order.getPrice()){
						throw new ServletException();
					}
					session.evict(company);
					company.setBalance(company.getBalance()-(long)(order.getPrice()*0.8));
					company.setIncome(company.getIncome()-(long)(order.getPrice()*0.8));
					company.setRecordDate(System.currentTimeMillis());
					company.setStatus(2);
					company.setMoney(-order.getPrice());
					company.setAboutId(order.getOrderId());
					session.save(company);
					
					AccountRecordBean record = new AccountRecordBean();
					CatInfoBean cat = (CatInfoBean) session.get(CatInfoBean.class, order.getCatId());
					record.setAccountId(cat.getUserId());
					record.setStatus(1);
					record.setOrderId(order.getOrderId());
					record.setPayAccountId(order.getPayUserId());
					record.setPrice((long)(order.getPrice()*0.8));
					record.setPayDate(System.currentTimeMillis());
					session.save(record);
					
					AccountInfoBean account = (AccountInfoBean) session.get(AccountInfoBean.class, cat.getUserId());
					account.setBalance(account.getBalance()+(long)(order.getPrice()*0.8));
					account.setExpense(account.getExpense()-(long)(order.getPrice()*0.8));
					
					if(CheckAccountUtil.check(session, account)&&CheckAccountUtil.checkCompany(company)){
						r.success(response);
					}else{
						throw new ServletException();
					}
				}
				break;
			case 5:
				if(source==0){
					order.setStatus(5);
					order.setSwitch5(System.currentTimeMillis());
				}
				break;
			case 6:
				if(source==1){
					order.setStatus(6);
					order.setSwitch6(System.currentTimeMillis());
				}
				break;
			default:
				throw new ServletException();
		}
		session.update(order);
		r.success(response);
	}
}
