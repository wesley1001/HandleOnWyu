package com.wuyuan.android.utils;

import java.io.Serializable;


public class HttpPostParam implements Serializable,Comparable<HttpPostParam> {
	private static final long serialVersionUID = 2721340807561333705L;
	private String name;
	private String Value;
	
	public HttpPostParam(){
		super();
	}
	public HttpPostParam(String name,String value){
		super();
		this.name = name;
		this.Value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	
	@Override
	public String toString() {
		return "Parameter [name=" + name + ", Value=" + Value + "]";
	}
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(null == arg0){
			return false;
		}
		if(this == arg0){
			return true;
		}
		if(arg0 instanceof HttpPostParam){
			HttpPostParam param = (HttpPostParam) arg0;
			return this.getName().equals(param.getName())  &&  this.getValue().equals(param.getValue());
		}
		return false;
	}
	
	public int compareTo(HttpPostParam param) {
		// TODO Auto-generated method stub
		int compared;
		compared = name.compareTo(param.getName());
		if(0 ==compared){
			compared = Value.compareTo(param.getValue());
		}
		return compared;
	}
}

