package com.demo.camel.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class HttpBackProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("back header::::::::::::"+exchange.getIn().getHeaders());

	}

}
