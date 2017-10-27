package com.smq.demo5.bean;

public class AssetRecordBean implements BaseBean{

	private int assetId;
	private int accountId;
	private int status;
	private long price;
	private long assetDate;
	private AccountInfoBean account;
	
	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getAssetDate() {
		return assetDate;
	}

	public void setAssetDate(long assetDate) {
		this.assetDate = assetDate;
	}

	public AccountInfoBean getAccount() {
		return account;
	}

	public void setAccount(AccountInfoBean account) {
		this.account = account;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
