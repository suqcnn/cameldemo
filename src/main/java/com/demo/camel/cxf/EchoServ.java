package com.demo.camel.cxf;

//@javax.jws.WebService(serviceName = "EchoServService", portName = "EchoServPort", targetNamespace = "http://webservices.cxf.demo/", endpointInterface = "com.demo.camel.cxf.EchoServ")
public class EchoServ {
	public String echo(String str) {
		return "recieved" + str;
	}
}
