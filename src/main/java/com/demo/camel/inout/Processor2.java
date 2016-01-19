package com.demo.camel.inout;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Processor2 implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("\nprocessor2,do nothing.....\n");
	}

}
