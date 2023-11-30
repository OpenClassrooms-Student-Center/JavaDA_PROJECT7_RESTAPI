package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nnk.springboot.domain.User;

import java.util.Collection;

@Controller
public class HomeController
{
	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String home(Model model, HttpServletRequest request, Authentication authentication)
	{
		User remoteUser = userService.findByUsername(request.getRemoteUser());
		request.getSession().setAttribute("remoteUser", remoteUser.getUsername());
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if(authorities!=null && !authorities.isEmpty()) {
			if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
				return "redirect:/admin/home";
			} else if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
				return "redirect:/user/home";
			}
		}
		return "403";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/user/list";
	}

	@RequestMapping("/user/home")
	public String userHome(Model model)
	{
		return "redirect:/bidList/list";
	}

}
