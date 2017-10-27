package com.smq.demo5.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.smq.demo5.bean.AccountCompanyBean;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.AccountRecordBean;
import com.smq.demo5.bean.AssetRecordBean;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

public class Outpour extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		int userId = Integer.parseInt(request.getParameter("userId"));
		long money = Long.parseLong(request.getParameter("money"));
		//1 �ڳ�ֵ���ֱ����һ����¼
		AssetRecordBean asset = new AssetRecordBean();
		asset.setAccountId(userId);
		asset.setStatus(2);
		asset.setPrice(money);
		asset.setAssetDate(System.currentTimeMillis());
		session.save(asset);
		//2 �ڹٷ��˻������һ����¼ ��¼�޸ĺ�Ľ��
		AccountCompanyBean company = (AccountCompanyBean) session.createQuery("from AccountCompanyBean order by recordId desc").setFirstResult(0).setMaxResults(1).uniqueResult();
		session.evict(company);
		//2.1 ����ٷ��˻��İ�ȫ�� balance = asset + liability + income - expense   balance > liability
		long sum = company.getAsset() + company.getLiability() + company.getIncome() - company.getExpense();
		if(sum != company.getBalance() || company.getBalance() < company.getLiability()){
			r.error(response, "��ֵʧ��");
			throw new ServletException();
		}
		//2.2 ��������޸ĺ���˻��Ƿ�ȫ 
		company.setRecordDate(System.currentTimeMillis());
		company.setStatus(1);
		company.setMoney(-money);
		company.setAboutId(asset.getAssetId());
		company.setBalance(company.getBalance()-money);
		company.setLiability(company.getLiability()-money);
		long sum2 = company.getAsset() + company.getLiability() + company.getIncome() - company.getExpense();
		if(sum2 != company.getBalance() || company.getBalance() < company.getLiability()){
			r.error(response,"��ֵʧ��");
			throw new ServletException();
		}
		//2.3 ��ȫ������޸� ����ȫ��ع�������
		session.save(company);
		//3 �Ը����˻������޸�
		AccountInfoBean account = (AccountInfoBean) session.createQuery("from AccountInfoBean where accountId=?").setParameter(0, userId).uniqueResult();
		session.evict(account);
		//3.1 ��������˻��İ�ȫ�� expense asset
//		if(!check(session,account,userId)){
//			r.error(response, "��ֵʧ��");
//			return;
//		}
		account.setBalance(account.getBalance()-money);
		account.setAsset(account.getAsset()-money);
		//3.2 ��������޸ĺ���˻��Ƿ�ȫ
		if(!check(session,account,userId)){
			r.error(response, "��ֵʧ��");
			throw new ServletException();
		}
		//3.3 ��ȫ������޸� ����ȫ��ع�������
		session.update(account);
		//4 ��ֵ�ɹ� ���ص�ǰ�˻�����Ϣ
		r.success(response);
		
	}
	
	public boolean check(Session session,AccountInfoBean account,int userId){
		long expense = 0;
		List<AccountRecordBean> list1 = session.createQuery("from AccountRecordBean where accountId=?").setParameter(0, userId).list();
		for(AccountRecordBean bean:list1){
			if(bean.getStatus()==1){
				expense -= bean.getPrice();
			}else if (bean.getStatus()==2) {
				expense += bean.getPrice();
			}
		}
		long assetNum =0;
		List<AssetRecordBean> list2 = session.createQuery("from AssetRecordBean where accountId=?").setParameter(0, userId).list();
		for(AssetRecordBean bean:list2){
			if(bean.getStatus()==1){
				assetNum += bean.getPrice();
			}else if (bean.getStatus()==2) {
				assetNum -= bean.getPrice();
			}
		}
		if(account.getExpense() != expense || account.getAsset()!=assetNum || account.getBalance()!=account.getAsset() - account.getExpense()){
			return false;
		}
		return true;
	}
	
}
