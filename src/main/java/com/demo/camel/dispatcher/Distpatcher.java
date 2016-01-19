package com.demo.camel.dispatcher;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class Distpatcher {

	public static void main(String[] args) throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("process", new ConvertProcess());
		CamelContext camelContext = new DefaultCamelContext(registry);
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				/*Predicate predicate = new Predicate(){

					@Override
					public boolean matches(Exchange exchange) {
						
						return "200".equalsIgnoreCase((exchange.getIn().getHeader("CamelHttpResponseCode",String.class)));
					}
					
				};*/
				from("kafka:192.168.88.10:9092?topic=test&zookeeperHost=192.168.88.10&zookeeperPort=2181&groupId=group1").
				processRef("process").removeHeaders("*").loadBalance()./*.recipientList("recipientList")*/to("jetty:http://127.0.0.1:8081").process(new HttpBackProcessor())
				/*.onException(Exception.class).maximumRedeliveryDelay(100).maximumRedeliveries(5)*/;
//				from("").to("");
				from("jetty:http://127.0.0.1:8081").log("http server end");
			}
		});
		camelContext.start();
		Thread.sleep(100000000);
		camelContext.stop();
	}

}
