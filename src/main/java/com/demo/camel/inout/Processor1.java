package com.demo.camel.inout;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Processor1 implements Processor {

	public void process(Exchange exchange) throws Exception {
		exchange.getOut().setBody("this is response content");
		System.out.println("\nset response in processor1\n");
	}

}
