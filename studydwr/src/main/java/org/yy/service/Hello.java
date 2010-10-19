package org.yy.service;

public class Hello {
	
	public String hello(String name){
		
		return "哈罗" + name;
	}
	
	public Name getBean(String myname){
		
		Name name = new Name();
		name.setName(myname);
		name.setChineseName("李小明");
		name.setEnglishName("Xiaoming Lee");
		return name;
	}
}
