package com.smq.demo5.bean;

public class AccountRecordBean implements BaseBean {
	
	int recordId;
	int accountId;
	int status;
	int orderId;
	int payAccountId;
	long price;
	long payDate;
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getPayAccountId() {
		return payAccountId;
	}

	public void setPayAccountId(int payAccountId) {
		this.payAccountId = payAccountId;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getPayDate() {
		return payDate;
	}

	public void setPayDate(long payDate) {
		this.payDate = payDate;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
