package com.demo.camel.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ConvertProcess implements Processor{
	public void process(Exchange exchange) throws Exception {
//		exchange.getIn().setHeader("h_recip_proxyUrl", "jetty:///");
		// TODO Auto-generated method stub
		System.out.println("convertprocessor::::"+exchange.getIn().getBody().getClass());
		System.out.println("convertprocessor,value::::"+exchange.getIn().getBody());
		exchange.getIn().setBody("aaaaaaa");
	}
}
