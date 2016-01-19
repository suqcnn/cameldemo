package com.demo.camel.transform;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TransformTest {
	public static void main(String args[]) throws Exception{
		CamelContext camelcontext = new DefaultCamelContext();
		camelcontext.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
//					from("direct:start").transform(body().regexReplaceAll("\n", "<br/>")).process(new LogProcessor())/*to("log:xx::::::::::::::::${in}")*/;
					from("direct:start").transform(new CustomExpression()).process(new LogProcessor());
				}
			});
		camelcontext.start();
		String body = "a\nb\nc";

		camelcontext.createProducerTemplate().sendBody("direct:start", body);
//		if(true){
//			Thread.sleep(100000000);
//		}
		
		camelcontext.stop();
		System.out.println("end!");
	}
	
}

class LogProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		System.out.println("log:::::::::::::::::::::"+exchange.getIn().getBody());
		
	}
	
}

class CustomExpression implements Expression{

	public <T> T evaluate(Exchange exchange, Class<T> type) {
		String body = exchange.getIn().getBody(String.class);
		body = body.replaceAll("\n", "</br>");
		System.out.println("expression done...........................................");
		return (T)body;
	}
	
}
