package com.demo.camel.expression;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class TestExpression {

	public static void main(String[] args) throws Exception {
		// org.apache.camel.language.simple.ast.SimpleFunctionExpression
		SimpleRegistry simpleRegistry = new SimpleRegistry();
		simpleRegistry.put("UUIDGen", new UUIDGen());
		CamelContext camelContext = new DefaultCamelContext(simpleRegistry);
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				//
				from("direct:a").log("::\n\n$simple{in.body},,${exchangeId},,${in.headers},,${threadName};;;;;;;;;;;\n\n\n");
				from("direct:b").log("::\n\n${bodyAs(com.demo.camel.expression.Student)},,;;;;;;;;;;;\n\n\n");
				from("direct:c").log("::\n\n${date:now:yyyy-MM-dd HH:mm:ss},,;;;;;;;;;;;\n\n\n");
				from("direct:d").log("::\n\n${bean:UUIDGen?method=getID},,;;;;;;;;;;;\n\n\n");
				from("direct:e").bean(new SimpleProcessor()).log("e is end");
//				from("file://inbox?noop=true").log("read file,,,,,,,").log("::\n\npicked up:${file:name},,;;;;;;;;;;;\n\n\n");
				from("file://E:/mye2015/mye2015-iteinm/cameldemo1/src/main/resources/inbox?noop=true").log("read file,,,,,,,").log("::\n\npicked up:${file:name},,;;;;;;;;;;;\n\n\n");
				
			}
		});
		camelContext.start();
		camelContext.createProducerTemplate().sendBody("direct:a","hello");
		camelContext.createProducerTemplate().sendBody("direct:b",new Student());
		camelContext.createProducerTemplate().sendBody("direct:c","print now");
		camelContext.createProducerTemplate().sendBody("direct:d","print uuid");
		camelContext.createProducerTemplate().sendBody("direct:e",new Student());

//		Thread.sleep(2000000);
//		camelContext.stop();
	}

}


