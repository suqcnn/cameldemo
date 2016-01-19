package com.demo.camel.expression;

public class Student{
	private String name = "student1";
	private int age = 15;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String sayHello(){
		return "hello from student";
	}
}