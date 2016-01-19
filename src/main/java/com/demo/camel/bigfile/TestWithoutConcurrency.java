package com.demo.camel.bigfile;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestWithoutConcurrency {
	public static void main(String args[]) throws Exception{
		CamelContext camelcontext = new DefaultCamelContext();
		camelcontext.addRoutes(createRouteBuilder());
		
		NotifyBuilder notify = new NotifyBuilder(camelcontext).from("file*").whenDone(1).create();

        long start = System.currentTimeMillis();

        notify.matches(5 * 60, TimeUnit.SECONDS);

        long delta = System.currentTimeMillis() - start;
        System.out.println("Took " + delta / 1000 + " seconds");
        
        camelcontext.start();
	}
	protected static RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	from("file:/target/iventory?noop=true").log("Starting to process big file: ${header.CamelFileName}")
            	.split(body().tokenize("\n")).streaming().bean(InventoryService.class, "csvToObject").to("direct:update").end().log("Done processing big file: ${header.CamelFileName}");
            	
            	from("direct:update").bean(InventoryService.class, "updateInventory");
            }
		};
	}
}
