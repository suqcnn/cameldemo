package com.demo.camel.pattern.messagingsystems;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class Message {

	public static void main(String[] args) throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("process", new ProcessBean());
		CamelContext camelContext = new DefaultCamelContext(registry);
		System.out.println("------------------------------------------------------>\n\n\n\n\n"+camelContext.findEips());
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:startInOnly").inOnly("bean:process");
				from("direct:startInOut").inOut("bean:process");
				from("direct:startInOutBooleanPara").inOut("bean:process?method=method3");
			}
		});
		camelContext.start();
		//InOnly
		camelContext.createProducerTemplate().sendBody("direct:startInOnly","Hello World");
		//InOut
		camelContext.createProducerTemplate().requestBody("direct:startInOut","Hello World");
		
		//additional , will be call ProcessBean.method3
		camelContext.createProducerTemplate().requestBody("direct:startInOutBooleanPara",Boolean.TRUE);
		camelContext.stop();
	}

}
