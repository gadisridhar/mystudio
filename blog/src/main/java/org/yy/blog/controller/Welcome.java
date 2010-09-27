package org.yy.blog.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yy.blog.dao.UserDao;
import org.yy.blog.model.User;

@Controller
@RequestMapping("/*")
public class Welcome {
	@Inject
	private UserDao userDao;
	
	@RequestMapping("index")
	public String index(){
		User u = new User();
		u.setUserName("yy");
		u.setEmail("yy@163.com");
		u.setCountry("China");
		userDao.create(u);
		return "index";
	}
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
