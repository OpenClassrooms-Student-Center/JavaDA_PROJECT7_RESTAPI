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
	private final UserService userService;

	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This method redirects the user to the home page corresponging to its role
	 * @param model
	 * @param request
	 * @param authentication
	 * @return A String corresponding to a thymeleaf template
	 */
	@RequestMapping("/")
	public String home(Model model, HttpServletRequest request, Authentication authentication)
	{
		User remoteUser = userService.findByUsername(request.getRemoteUser());
		request.getSession().setAttribute("remoteUser", remoteUser.getUsername());
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if(authorities!=null && !authorities.isEmpty()) {
			if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
				return "redirect:/home/admin";
			} else if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
				return "redirect:/home/user";
			}
		}
		return "error/403";
	}

	/**
	 * This method redirects to the admin home page
	 * @param model
	 * @return A String corresponding to a thymeleaf template
	 */
	@RequestMapping("/home/admin")
	public String adminHome(Model model)
	{
		return "redirect:/user/list";
	}

	/**
	 * This method redirects to the user home page
	 * @param model
	 * @return A String corresponding to a thymeleaf template
	 */
	@RequestMapping("/home/user")
	public String userHome(Model model)
	{
		return "redirect:/bidList/list";
	}

}
