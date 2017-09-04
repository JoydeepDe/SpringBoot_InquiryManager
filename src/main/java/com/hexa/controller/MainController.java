package com.hexa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index");
		return model;
	}

}
