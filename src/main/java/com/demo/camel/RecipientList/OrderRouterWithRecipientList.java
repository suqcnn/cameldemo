package com.demo.camel.RecipientList;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;

public class OrderRouterWithRecipientList {
	public static void main(String args[]) throws Exception{
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
	        context.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
	        context.addRoutes(new RouteBuilder() {
	            @Override
	            public void configure() {
	            	from("file:src/data?noop=true").to("jms:incomingOrders");
	            	
	            	from("jms:incomingOrders")
	                .choice()
	                    .when(header("CamelFileName").endsWith(".xml"))
	                        .to("jms:xmlOrders")
	                    .when(header("CamelFileName").regex("^.*(csv|csl)$"))
	                        .to("jms:csvOrders")
	                    .otherwise()
	                        .to("jms:badOrders"); 
	            	
	            	from("jms:xmlOrders").setHeader("customer",xpath("/order/@customer")).process(new Processor() {
	                    public void process(Exchange exchange) throws Exception {
	                        String recipients = "jms:accounting";
	                        String customer = exchange.getIn().getHeader("customer", String.class);
	                        if (customer.equals("honda")) {
	                            recipients += ",jms:production";
	                        }
	                        exchange.getIn().setHeader("recipients", recipients);
	                    }
	                }).recipientList(header("recipients"));
	            	
	            	from("jms:accounting").process(new Processor() {
	                    public void process(Exchange exchange) throws Exception {
	                        System.out.println("Accounting received order: " 
	                                + exchange.getIn().getHeader("CamelFileName"));   
	                    }
	                });  
	            	
	            	from("jms:production").process(new Processor() {
	                    public void process(Exchange exchange) throws Exception {
	                        System.out.println("Production received order: " 
	                                + exchange.getIn().getHeader("CamelFileName"));   
	                    }
	                });
	            	
	            }
	        });
	        context.start();
	        Thread.sleep(10000);
	        context.stop();
	}
}
