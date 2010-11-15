package org.yy.service;

public class Hello {
	
	public String hello(String name){
		
		return "นþยÞ" + name;
	}
	
	public Name getBean(String myname){
		
		Name name = new Name();
		name.setName(myname);
		name.setChineseName("ภ๎ะกร๗");
		name.setEnglishName("Xiaoming Lee");
		return name;
	}
}
