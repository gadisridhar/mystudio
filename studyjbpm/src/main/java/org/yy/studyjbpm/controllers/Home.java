package org.yy.studyjbpm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home/*")
public class Home {
	@RequestMapping("index")
	public @ResponseBody String index(){
		return "hello";
	}
}
