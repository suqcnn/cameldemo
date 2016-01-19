package com.demo.camel.marshal;

import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.camel.marshal.model.Customer;

public class MarshalTest {
	private static ApplicationContext ac;

	public static void main(String args[]) throws Exception{
		ac = new ClassPathXmlApplicationContext("camel/camel-marshal.xml");
		Customer body = new Customer();
		body.setAge(30);
		body.setId(12345678);
		body.setName("unknow");
		ProducerTemplate camelTemplate = ac.getBean("camelTemplate",ProducerTemplate.class);
		camelTemplate.sendBody("direct:mashrshalTest", body);
//		if(true){
//			Thread.sleep(100000000);
//		}
		
//		camelcontext.stop();
		System.out.println("end!");
	}
	
}



