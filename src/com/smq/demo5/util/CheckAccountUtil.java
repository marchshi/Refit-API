package com.smq.demo5.util;

import java.util.List;

import javax.servlet.ServletException;

import org.hibernate.Session;

import com.smq.demo5.bean.AccountCompanyBean;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.AccountRecordBean;
import com.smq.demo5.bean.AssetRecordBean;

public class CheckAccountUtil {

	public static boolean check(Session session,AccountInfoBean account){
		int userId = account.getAccountId();
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
	
	public static boolean checkCompany(AccountCompanyBean company){
		long sum = company.getAsset() + company.getLiability() + company.getIncome() - company.getExpense();
		if(sum != company.getBalance() || company.getBalance() < company.getLiability()){
			return false;
		}else{
			return true;
		}
	}
	
}
