package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.service.LoggerApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

	// Récupération de notre logger.
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

	@Autowired
	private LoggerApi loggerApi;

	/**
	 * @param request
	 * @param response
	 * @return String
	 */
	@Secured({ "USER", "ADMIN" })
	@RequestMapping("/")
	public String home(HttpServletRequest request, HttpServletResponse response) {

		String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
		LOGGER.info(messageLoggerInfo);

		return "home";
	}

	/**
	 * @param request
	 * @param response
	 * @return String
	 */
	@Secured("ADMIN")
	@RequestMapping("/admin/home")
	public String adminHome(HttpServletRequest request, HttpServletResponse response) {

		String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
		LOGGER.info(messageLoggerInfo);

		return "redirect:/bidList/list";
	}

}
