package com.smq.demo5.util;

public class BaseJson {
	
	public String type ="";
	public String content ="";
	public String data ="";
	
	public BaseJson(){}
	
	public BaseJson(String type, String content, String data) {
		super();
		this.type = type;
		this.content = content;
		this.data = data;
	}
}
