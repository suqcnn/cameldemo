package com.demo.camel.expression;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.SimpleBuilder;

public class SimpleProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
//		exchange.getIn().getBody().hashCode()
		
		String simpleExpression = "${body.sayHello()}";//will call student's method
		SimpleBuilder simple = new SimpleBuilder(simpleExpression);
		System.out.println("SimpleProcessor,,,,,,,,,,,,,,,,,,,,,,,,"+simple.evaluate(exchange,String.class));
	}

}
