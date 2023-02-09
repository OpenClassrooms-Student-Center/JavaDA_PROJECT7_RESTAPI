package com.nnk.springboot.controllers;

import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String home() {

		// Init Account Administrator
		User user = new User(1,"admin","qsdfghjklmù$9834efq","Administrator","ADMIN");

		// if User Administrator is not present, Create Account
		if (!userRepository.findById(1).isPresent()){
			userRepository.save(user);
			logger.info("user save before leaving");
		}

		return "home";
	}


}
