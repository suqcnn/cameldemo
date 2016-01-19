package com.demo.camel.log;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
class LogProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		System.out.println("log:::::::::::::::::::::"+exchange.getIn().getBody());
	}
	
}
