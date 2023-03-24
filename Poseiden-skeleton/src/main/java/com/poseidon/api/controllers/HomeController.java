package com.poseidon.api.controllers;

import com.poseidon.api.model.User;
import com.poseidon.api.repositories.UserRepository;
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
		Long id = Long.valueOf(1);
		User user = new User(id,"admin","qsdfghjklmù$9834efq","Administrator","ADMIN");

		// if User Administrator is not present, Create Account
		if (!userRepository.findById(id).isPresent()){
			userRepository.save(user);
			logger.info("user save before leaving");
		}

		return "home";
	}


}
