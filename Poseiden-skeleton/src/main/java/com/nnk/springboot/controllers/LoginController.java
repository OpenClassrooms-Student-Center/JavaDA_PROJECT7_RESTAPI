package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.annotation.security.RolesAllowed;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringSecurityConfig securityConfig;

    public LoginController() {
    }

    public LoginController(ModelAndView modelAndView, UserRepository userRepository,
            SpringSecurityConfig securityConfig) {
        this.modelAndView = modelAndView;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public ModelAndView getModelAndView() {
        return this.modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SpringSecurityConfig getSecurityConfig() {
        return this.securityConfig;
    }

    public void setSecurityConfig(SpringSecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @RolesAllowed("USER")
    @GetMapping("/*")
    public ModelAndView afterLogin(

            Principal principal) {

        return modelAndView;
    }

    @RolesAllowed("USER")
    @GetMapping("/login")
    public String afterLogin(
            Model model,
            Principal principal) {

        return "login";
    }

    @RolesAllowed("USER")
    @PostMapping("/login/validate")
    public String validate(Model model, Principal principal) {

        return "redirect:/home";
    }

    @RolesAllowed("USER")
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @RolesAllowed("USER")
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    public String getUserInfo(Principal user) {
        StringBuilder userInfo = new StringBuilder();
        if (user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        }
        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append(u.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

}
