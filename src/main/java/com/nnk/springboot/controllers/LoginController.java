package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    ModelAndView mav = new ModelAndView();

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private SpringSecurityConfig securityConfig;

    @Autowired
    private UsersService userService;

    @Autowired
    private LoggerApi loggerApi;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    public LoginController() {
    }

    public LoginController(ModelAndView mav, UsersRepository userRepository, SpringSecurityConfig securityConfig,
            UsersService userService, LoggerApi loggerApi) {
        this.mav = mav;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.userService = userService;
        this.loggerApi = loggerApi;
    }

    /**
     * @return ModelAndView
     */
    public ModelAndView getMav() {
        return this.mav;
    }

    
    /** 
     * @param mav
     */
    public void setMav(ModelAndView mav) {
        this.mav = mav;
    }

    public UsersService getUserService() {
        return this.userService;
    }

    public void setUserService(UsersService userService) {
        this.userService = userService;
    }

    public LoggerApi getLoggerApi() {
        return this.loggerApi;
    }

    public void setLoggerApi(LoggerApi loggerApi) {
        this.loggerApi = loggerApi;
    }

    public UsersRepository getUserRepository() {
        return this.userRepository;
    }

    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SpringSecurityConfig getSecurityConfig() {
        return this.securityConfig;
    }

    public void setSecurityConfig(SpringSecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Secured({ "USER", "ADMIN" })
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "home";
    }

    @GetMapping("login")
    public ModelAndView login(Principal principal, HttpServletRequest request, HttpServletResponse response) {

        mav.setViewName("login");

        String messageLoggerInfo = "URI active : /login Code response : "
                + HttpStatus.valueOf(response.getStatus());
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("login/ok")
    public ModelAndView loginOk(Model model, HttpServletRequest request, HttpServletResponse response) {

        mav.setViewName("home");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @PostMapping("app-logout")
    public ModelAndView appLogout(Model model, Principal principal, HttpServletRequest request,
            HttpServletResponse response) {

        mav.setViewName("login");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    @Secured("ADMIN")
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles(HttpServletRequest request, HttpServletResponse response) {

        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("error")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {

        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("/error/403");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

}
