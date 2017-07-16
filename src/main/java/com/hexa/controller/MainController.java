package com.hexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hexa.model.User;
import com.hexa.model.dao.UserDAO;

@Controller
public class MainController {

	@Autowired
	private UserDAO userDao;

	@RequestMapping("/")
	public ModelAndView index() {

		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("index");
		model.addObject("userList", listUsers);
		return model;
	}

}
