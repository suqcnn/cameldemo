package com.demo.camel.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.Processor;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JsonTest {
	public static void main(String args[]) throws Exception{
		CamelContext camelcontext = new DefaultCamelContext();
		camelcontext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer://foo?fixedRate=true&period=2000").process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("cun content:::::"+exchange.getIn().getBody());
//						List list = new ArrayList();
//						Map header = new HashMap();
//						header.put("index", "aaa");
//						header.put("type", "bbb");
//						Map content = new HashMap();
//						content.put("id", "111");
//						content.put("name", "222");
//						list.add(header);
//						list.add(content);
//						exchange.getIn().setBody(list);
						
						Map all = new HashMap();
						Map header = new HashMap();
						header.put("index", "aaa");
						header.put("type", "bbb");
						Map content = new HashMap();
						content.put("id", "111");
						content.put("name", "222");
						all.put("key1", content);
						all.put("key2",header);
						System.out.println(":::"+new org.codehaus.jackson.map.ObjectMapper().writeValueAsString(all));
						exchange.getIn().setBody(all);
					}
				}).marshal().json(JsonLibrary.Jackson).process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("cun content:::::"+exchange.getIn().getBody(String.class));
					}
				});
			}
		});
		camelcontext.start();
		if(true){
			Thread.sleep(100000000);
		}
		camelcontext.start();
		System.out.println("end!");
	}
}
