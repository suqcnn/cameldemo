package com.demo.camel.pattern.messagingsystems;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class PipesAndFilters {
	public static void main(String args[]) throws Exception{
		pipelines();
	}
	
	private static void pipelines() throws Exception{
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				//
				from("direct:a").pipeline("direct:x","direct:y","direct:z","mock:result.........");
				from("direct:x").log("directx::\n\n$simple{in.body},,${exchangeId},,${in.headers},,${threadName};;;;;;;;;;;\n\n\n");//log is ok
				from("direct:y").to("log:directy::${in.body};;;;;;;;;;;");//log is not correctly
				from("direct:z").to("log:directz;;;;;;;;;;;");
			}
		});
		camelContext.start();
		camelContext.createProducerTemplate().sendBody("direct:a","hello");
		camelContext.stop();
	}
	
	private static void filters(){
		
	}
}
