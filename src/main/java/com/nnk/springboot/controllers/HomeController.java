package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	// Récupération de notre logger.
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

	@RequestMapping("/*")
	public String home(Model model) {
		return "home";
	}

	@Secured("ADMIN")
	@RequestMapping("/admin/home")
	public String adminHome(Model model, HttpServletRequest request) {

		String messageLoggerInfo = "URI active : " + request.getRequestURI() + " User role : "
				+ request.isUserInRole("USER") + " "
				+ " Info user : " + request.getUserPrincipal();
		LOGGER.info(messageLoggerInfo);

		return "redirect:/bidList/list";
	}

}
