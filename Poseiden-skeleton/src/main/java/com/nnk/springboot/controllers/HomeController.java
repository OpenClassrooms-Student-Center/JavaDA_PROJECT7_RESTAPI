package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nnk.springboot.domain.User;

@Controller
public class HomeController
{
	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String home(Model model, HttpServletRequest request)
	{
		User remoteUser = userService.findByUsername(request.getRemoteUser());
		request.getSession().setAttribute("remoteUser", remoteUser.getUsername());
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
