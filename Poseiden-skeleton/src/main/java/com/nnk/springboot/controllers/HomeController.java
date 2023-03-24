package com.nnk.springboot.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@RequestMapping("/")
	@RolesAllowed("USER")
	public String home(Model model)
	{
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
