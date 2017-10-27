package com.smq.mode2.util;

public class BaseJson {
	
	public String type = "error";
	public String content = "invalid";
	public String data;
	
	@Override
	public String toString() {
		return "{\"message\":{\"type\":\""+type+"\",\"content\":\""+content+"\"},\"data\":"+data+"}";
	}

}
