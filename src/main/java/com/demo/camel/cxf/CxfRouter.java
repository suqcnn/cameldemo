package com.demo.camel.cxf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.Processor;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class CxfRouter {
	public static void main(String args[]) throws Exception{
		CamelContext camelcontext = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		camelcontext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
//		CxfEndpoint cxfendpoint = new CxfEndpoint();
//		cxfendpoint.setAddress("http://localhost:8080/perftest");
//		cxfendpoint.setServiceClass(EchoServ.class);
//		cxfendpoint.setServiceClass(new EchoServ());
//		cxfendpoint.setServiceName(new QName("EchoServService"));
//		cxfendpoint.setPortName(new QName("EchoServPort"));
		camelcontext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
//				ExecutorService executor1 = Executors.newFixedThreadPool(6); 
//				ExecutorService executor = Executors.newFixedThreadPool(16); 
//				from("cxf://cxfendpoint?serviceClass=com.demo.camel.cxf.EchoServ&address=http://localhost:8080/perftest").multicast().parallelProcessing().executorService(executor1).to("jms:cvs").process(new Processor(){
					from("cxf://cxfendpoint?serviceClass=com.demo.camel.cxf.EchoServ&address=http://localhost:8080/perftest").to("seda:send2jms?waitForTaskToComplete=Never").process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("cun content:::::"+exchange.getIn().getBody());
					}
					
				});
					
				from("seda:send2jms?waitForTaskToComplete=Never").to("jms:cvs");
				
				from("jms:cvs").process(new Processor(){

					public void process(Exchange exchange) throws Exception {
						System.out.println("thread id:::::"+Thread.currentThread().getId());
						System.out.println("qu content:::::"+exchange.getIn().getBody());
					}
				});
			}
		});
		camelcontext.start();
		if(true){
			Thread.sleep(100000000);
		}
		
		camelcontext.stop();
		System.out.println("end!");
	}
}
