package com.demo.camel.exec;

import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecLocalTest {
	private static ApplicationContext ac;

	public static void main(String args[]) throws Exception{
		ac = new ClassPathXmlApplicationContext("camel/camel-remotecmd.xml");
		ProducerTemplate camelTemplate = ac.getBean("camelTemplate",ProducerTemplate.class);
		camelTemplate.sendBody("direct:execLocal",null);
//		if(true){
//			Thread.sleep(100000000);
//		}
		
		System.out.println("end!");
	}
	
}



