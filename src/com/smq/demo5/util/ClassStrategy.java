package com.smq.demo5.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ClassStrategy implements ExclusionStrategy  {
	
	public Class bean;
	
	public ClassStrategy(Class bean){
		this.bean = bean;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> c) {
		if(c.getName().equals(bean.getName()))
			return false;
		if(c.getName().contains("NameBean"))
			return false;
		if(c.getName().contains("Bean"))
			return true;
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		return false;
	}
	
	
	
}
