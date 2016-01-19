package com.demo.camel.expression;

import java.util.UUID;

public class UUIDGen{
	public String getID(){
		return UUID.randomUUID().toString();
	}
}