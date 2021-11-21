package com.nnk.springboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{

	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bid/list";
	}


}
