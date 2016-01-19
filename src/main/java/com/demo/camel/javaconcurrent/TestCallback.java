package com.demo.camel.javaconcurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SynchronizationAdapter;
import org.apache.camel.spi.Synchronization;

public class TestCallback {

	public static void main(String[] args) throws Exception {
		final List<String> relates = new ArrayList<String>();
		final Synchronization callback = new SynchronizationAdapter(){
			
			CountDownLatch countDownLatch = new CountDownLatch(5);
			@Override
			public void onComplete(Exchange exchange) {
				System.out.println("onComplete::"+exchange.getOut().getBody(String.class));
				relates.add(exchange.getOut().getBody(String.class));
				countDownLatch.countDown();
			}

			@Override
			public void onFailure(Exchange exchange) {
				System.out.println("onFailure::"+exchange.getOut().getBody(String.class));
				countDownLatch.countDown();
			}

			@Override
			public void onDone(Exchange exchange) {
				System.out.println("onDone::"+exchange.getOut().getBody(String.class));
				super.onDone(exchange);
			}
			
		};
		
		CamelContext camelcontext = new DefaultCamelContext();
		camelcontext.addRoutes(createRouteBuilder());
		camelcontext.start();
		ProducerTemplate producerTemplate = camelcontext.createProducerTemplate();
		for(int i=0;i<5;i++){
			System.out.println("send to seda...."+"seda:partner:" + i);
			producerTemplate.asyncCallbackRequestBody("seda:partner:" + i, "hehe", callback);
		}
		System.out.println("result1:"+relates);
		if(true){
			Thread.sleep(10000);
		}
		System.out.println("result2:"+relates);
		camelcontext.stop();
		System.out.println("end!");
		
	}
	
	protected static RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("seda:partner:0").log("begin partner:0").delay(500).transform().simple("bumper filter");

                from("seda:partner:1").log("begin partner:1").log("begin partner:0").delay(3000).transform().simple("nose panel");

                from("seda:partner:2").log("begin partner:2").delay(1000).transform().simple("bumper cover");

                from("seda:partner:3").log("begin partner:3").delay(250).transform().simple("bumper extension");

                from("seda:partner:4").log("begin partner:4").delay(2000).transform().simple("tow hooks");
            }
        };
    }

}
