package com.smq.demo5.bean;

public class AccountInfoBean implements BaseBean {
	
	private int accountId;
	private long balance;
	private long expense;
	private long asset;
	private UserInfoBean user;
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getExpense() {
		return expense;
	}

	public void setExpense(long expense) {
		this.expense = expense;
	}

	public long getAsset() {
		return asset;
	}

	public void setAsset(long asset) {
		this.asset = asset;
	}

	public UserInfoBean getUser() {
		return user;
	}

	public void setUser(UserInfoBean user) {
		this.user = user;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
