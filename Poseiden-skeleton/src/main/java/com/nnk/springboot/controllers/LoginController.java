package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling login and access-related requests.
 */
@Controller
public class LoginController {

    private final UserRepository userRepository;

    /**
     * Constructor for LoginController.
     *
     * @param userRepository the user repository
     */
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Displays the login page.
     *
     * @return the login view
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Retrieves and displays a list of users for authorized users (role = admin).
     *
     * @return the user list view
     */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Displays the access denied (403) error page.
     *
     * @return the 403 error view
     */
    @GetMapping("/403")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        mav.addObject("username", userDetails.getUsername());

        return mav;
    }
}
