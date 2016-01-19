package com.demo.camel.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatTest {
	public static void main(String args[]){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		System.out.println(dateFormat.format(new Date()));
	}
}
