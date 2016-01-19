package com.demo.camel.exec;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
class ExcecTcpRemoteProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		System.out.println("---------------->>>>"+exchange.getIn().getBody().getClass());
//        poetry.setPoet("Dr. Sarojini Naidu");
//        exchange.getOut().setBody(poetry);
	}
	
}
