package com.smq.demo5.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class FieldStrategy implements ExclusionStrategy {
    private String[] fields;

    @Override
    public boolean shouldSkipClass(Class<?> class1) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fieldattributes) {
        String f = fieldattributes.getName();
        boolean isSkip = false;
        if (isContains(fields, f)) {
        	isSkip = true;
        }
        return isSkip;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
    
    public boolean isContains(String[] fields,String f){
    	for(String str:fields){
    		if(str.equals(f))
    			return true;
    	}
    	return false;
    }
}