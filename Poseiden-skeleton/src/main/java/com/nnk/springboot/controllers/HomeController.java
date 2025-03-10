package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to handle home and admin home page requests.
 */
@Controller
public class HomeController {

	/**
	 * Displays the home page with the authenticated user's username.
	 *
	 * @param model the model to hold user information
	 * @return the home view
	 */
	@GetMapping("/")
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
		model.addAttribute("username", userDetails.getUsername() );
		return "home";
	}

	/**
	 * Redirects admins to the bid list page after authentication.
	 *
	 * @param model the model to hold user information
	 * @return redirect to the bid list view
	 */
	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
		model.addAttribute("username", userDetails.getUsername() );
		return "redirect:/bidList/list";
	}
}

