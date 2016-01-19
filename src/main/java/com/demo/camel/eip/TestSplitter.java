package com.demo.camel.eip;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestSplitter {

	public static void main(String[] args) throws Exception {
		CamelContext camelcontext = new DefaultCamelContext();
		
		camelcontext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").split(body()).streaming().log("split body:${body}").log("next------------${property.Exchange.SPLIT_INDEX}");
			}
		});
	camelcontext.start();
	List<String> body = new ArrayList<String>();
	body.add("a");
	body.add("b");
	body.add("c");
	camelcontext.createProducerTemplate().sendBody("direct:start", body);
	if(true){
		Thread.sleep(100000000);
	}
	
	camelcontext.stop();
	System.out.println("end!");
}

}
