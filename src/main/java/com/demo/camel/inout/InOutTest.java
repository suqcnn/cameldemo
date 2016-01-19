package com.demo.camel.inout;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class InOutTest {

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").bean(new Processor1()).bean(new Processor2()).log("\n end \n");
			}
		});
		camelContext.start();
		Object result = camelContext.createProducerTemplate().requestBody("direct:start","hello");
		System.out.println("\nresult is :::::::::"+result);
		System.out.println("\nbegin stop :::::::::::::::::\n");
		camelContext.stop();
	}

}
