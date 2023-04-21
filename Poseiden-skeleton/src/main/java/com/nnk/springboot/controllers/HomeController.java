package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

	@RequestMapping("/")
	public String home(Model model)
	{
		log.info("REQUEST /");
		return "home";
	}


}
