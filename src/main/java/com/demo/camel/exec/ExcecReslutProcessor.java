package com.demo.camel.exec;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.exec.ExecResult;
import org.apache.commons.io.IOUtils;
class ExcecReslutProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		ExecResult execResult = exchange.getIn().getBody(ExecResult.class);
		System.out.println("execResult::::::::::"+execResult);
		System.out.println("execResult.getExitValue()::::::::::"+execResult.getExitValue());
		System.out.println("execResult.getCommand()::::::::::"+execResult.getCommand());
		System.out.println("execResult.getStderr()::::::::::"+execResult.getStderr());
		System.out.println("execResult.getStdout()::::::::::"+execResult.getStdout());
		System.out.println("execResult.toString()::::::::::"+execResult.toString());
		InputStream inputStream = execResult.getStdout();
		if(inputStream!=null){
			String s = IOUtils.toString(inputStream);
			System.out.println("log,std:::::::::::::::::::::"+s);
		}
		InputStream inputStream1 = execResult.getStderr();
		if(inputStream1!=null){
			String s = IOUtils.toString(inputStream1);
			System.out.println("log,err:::::::::::::::::::::"+s);
		}
	}
	
}
