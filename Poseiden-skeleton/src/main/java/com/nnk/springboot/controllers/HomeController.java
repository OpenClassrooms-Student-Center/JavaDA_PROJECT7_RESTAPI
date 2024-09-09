package com.nnk.springboot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;

@Controller
public class HomeController{
	@RequestMapping("/")
	public String home(Model model,@AuthenticationPrincipal User user)
	{
		if(user.getRole().equals("ADMIN"))
			return "redirect:/admin/home";
		else
			return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
