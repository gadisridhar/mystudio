package org.yy.studyjbpm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/*")
public class Home {
	@RequestMapping("/home/index")
	public @ResponseBody String index(){
		return "hello";
	}
}
