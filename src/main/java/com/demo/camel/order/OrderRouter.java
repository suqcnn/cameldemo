package com.demo.camel.order;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.Processor;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class OrderRouter {
	public static void main(String args[]) throws Exception{
		CamelContext camelcontext = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		camelcontext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		camelcontext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:src/data?noop=true").to("jms:incomingOrders");
//				from("file:E:/mye2015/mye2015-iteinm/cameldemo1/src/data?noop=true").to("jms:incomingOrders");
				
				from("jms:incomingOrders").choice().when(header("CamelFileName").endsWith(".xml")).to("jms:xmlOrders")
				.when(header("CamelFileName").endsWith(".csv")).to("jms:csvOrders");
				
				from("jms:xmlOrders").process(new Processor(){

					public void process(Exchange exchange) throws Exception {
						System.out.println("xml file name:::::"+exchange.getIn().getHeader("CamelFileName"));
					}
					
				});
				
				from("jms:csvOrders").process(new Processor(){

					public void process(Exchange exchange) throws Exception {
						System.out.println("csv file name:::::"+exchange.getIn().getHeader("CamelFileName"));
					}
					
				});
			}
		});
		camelcontext.start();
		Thread.sleep(10000);
		camelcontext.stop();
		System.out.println("end!");
	}
}
