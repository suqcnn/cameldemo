package com.demo.camel.pattern.messagingsystems;

public class ProcessBean {
	public String method1(String arg){
		return "method1,recieved a massage:"+arg;
	}
	public String method2(boolean b){
		return "method2,";
	}
	public String method3(boolean b,String arg2){
		return "method3,"+b+","+arg2;
	}
}
