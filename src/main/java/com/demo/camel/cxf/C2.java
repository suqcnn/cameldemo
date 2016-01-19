package com.demo.camel.cxf;

public class C2 {
	public String getStr(){
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		for(StackTraceElement ste:st){
			System.out.println("getStr:\n"+ste.getClassName()+","+ste.getMethodName()+","+ste.getLineNumber());
		}
		getStatic();
		return "";
	}

	public static void getStatic() {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		for(StackTraceElement ste:st){
			System.out.println("getStatic:\n"+ste.getClassName()+","+ste.getMethodName()+","+ste.getLineNumber());
		}
		
	}
}
