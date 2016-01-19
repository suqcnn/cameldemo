package com.demo.camel.bianpai;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;


public class Bianpai {

	public static void main(String[] args) throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
//		registry.put("process", new ConvertProcess());
		CamelContext camelContext = new DefaultCamelContext(registry);
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
//				from("cxf://cxfendpoint?serviceClass=com.demo.camel.cxf.EchoServ&address=http://localhost:8080/perftest").to("seda:send2jms?waitForTaskToComplete=Never");
//				from("cxf://service1?serviceClass=com.demo.camel.bianpai.Service1&address=http://localhost:8080/service1").log("ok");
				from("cxf://service1?serviceClass=com.demo.camel.bianpai.Service1&address=http://localhost:8080/service1").to("xslt:bianpai/mytransform.xsl").to("http://localhost:8080/service2");
				from("cxf://service2?serviceClass=com.demo.camel.bianpai.Service2&address=http://localhost:8080/service2").log("ok");
//				from("cxf://serviceInterfacle?serviceClass=com.demo.camel.bianpai.ServiceInterface&address=http://localhost:8080/serviceInterface").to("");
//				from("").to("");
			}
		});
		camelContext.start();
		Thread.sleep(1000*1000);
		camelContext.stop();
	}

}
