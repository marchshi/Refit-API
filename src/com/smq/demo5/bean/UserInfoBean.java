package com.smq.demo5.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UserInfoBean implements BaseBean{
	
	private int userId;
	private String tel;
	private String name;
	private String profession;
	private String resume;
	private String touxiang;
	private String city;
	private long infoDate;
	//对应的账户信息
	private AccountInfoBean account;
	//对应的提问信息
	private Set<AskInfoBean> asks = new TreeSet<AskInfoBean>();
	//对应的类目信息
	private Set<CatInfoBean> cats = new TreeSet<CatInfoBean>();
	//对应的找人导购的订单
	private Set<OrderInfoBean> orders = new TreeSet<OrderInfoBean>();
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getInfoDate() {
		return infoDate;
	}

	public void setInfoDate(long infoDate) {
		this.infoDate = infoDate;
	}
	
	public AccountInfoBean getAccount() {
		return account;
	}

	public void setAccount(AccountInfoBean account) {
		this.account = account;
	}

	public Set<AskInfoBean> getAsks() {
		return asks;
	}

	public void setAsks(Set<AskInfoBean> asks) {
		this.asks = asks;
	}

	public Set<CatInfoBean> getCats() {
		return cats;
	}

	public void setCats(Set<CatInfoBean> cats) {
		this.cats = cats;
	}

	public Set<OrderInfoBean> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderInfoBean> orders) {
		this.orders = orders;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
}
