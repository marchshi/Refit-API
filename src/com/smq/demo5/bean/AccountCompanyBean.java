package com.smq.demo5.bean;

public class AccountCompanyBean implements BaseBean{
	
	private int recordId;
	private long balance;
	private long liability;
	private long income;
	private long expense;
	private long asset;
	private long recordDate;
	private int status;
	private long money;
	private int aboutId;

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getLiability() {
		return liability;
	}

	public void setLiability(long liability) {
		this.liability = liability;
	}

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
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

	public long getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(long recordDate) {
		this.recordDate = recordDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getAboutId() {
		return aboutId;
	}

	public void setAboutId(int aboutId) {
		this.aboutId = aboutId;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
