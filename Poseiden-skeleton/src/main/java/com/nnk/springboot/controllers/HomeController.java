package com.nnk.springboot.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

	@RequestMapping("/")
	//seulement USER?
	@RolesAllowed("USER")
	public String home(Model model)
	{
		log.info("REQUEST /");
		return "home";
	}
//
	@RequestMapping("/admin/home")
	@RolesAllowed("ADMIN")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
