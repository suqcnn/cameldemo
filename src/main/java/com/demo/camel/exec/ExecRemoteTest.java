package com.demo.camel.exec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ExecRemoteTest {
	private static ApplicationContext ac;

	public static void main(String args[]) throws Exception{
		ac = new ClassPathXmlApplicationContext("camel/camel-remotecmd.xml");
		while(true){
			Thread.sleep(100000000);
		}
//		System.out.println("end!");
	}
	
}



