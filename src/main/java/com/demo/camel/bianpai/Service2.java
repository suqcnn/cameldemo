package com.demo.camel.bianpai;

import javax.jws.WebService;

@WebService
public class Service2 {
//	public String serv2(String args,int i){
//		return "result from serv2:"+args+",i="+i;
//	}
	public String serv2(String args){
		return "result from serv2:"+args;
	}
}
