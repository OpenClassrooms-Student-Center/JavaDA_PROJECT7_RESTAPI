package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.UsersService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private SpringSecurityConfig securityConfig;

    @Autowired
    private UsersService userService;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
    LoggerApi loggerApi = new LoggerApi();

    public LoginController() {
    }

    public LoginController(ModelAndView modelAndView, UsersRepository userRepository,
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

    @RequestMapping("/*")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("login")
    public ModelAndView login(Principal principal) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("login/ok")
    public ModelAndView loginOk(Model model, Principal principal) {
        ModelAndView mav = new ModelAndView();

        modelHome(model, principal);

        mav.setViewName("home");
        return mav;
    }

    @Secured("USER")
    @PostMapping("login/validate")
    public String validate(Model model, Principal principal) {

        String messageLoggerInfo = "post after login PostMapping /login/validate";
        LOGGER.info(messageLoggerInfo + " pswd User : " + "???");

        return "redirect:/home";
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles(Model model, Principal principal, HttpServletRequest request) {

        modelHome(model, principal);

        String messageLoggerInfo = "getAllUserArticles GetMapping /secure/article-details";
        LOGGER.info(messageLoggerInfo);

        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("error")
    public ModelAndView error() {

        String messageLoggerInfo = "error GetMapping /error";
        LOGGER.info(messageLoggerInfo);

        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    private ModelAndView modelHome(Model model, Principal principal) {
        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(principal);
        if (nameUser == null) {
            int setStatus = 204;
            // setGetStatusModelAndView.setSetStatus(setStatus);
            // setGetStatusModelAndView.setHttpStatus(HttpStatus.NO_CONTENT);
            return modelAndView;
        } else {
            int setStatus = 200;
            // setGetStatusModelAndView.setSetStatus(setStatus);
            // setGetStatusModelAndView.setHttpStatus(HttpStatus.OK);
        }

        return modelAndView;
    }

    private Users recupererNameUser(Principal principal) {
        String idEmail = getUserInfo(principal);

        return userService.getUser(idEmail);
    }

    public String getUserInfo(Principal principal) {
        StringBuilder userInfo = new StringBuilder();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(principal));
        }
        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal principal) {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) principal);
        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append(u.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

}
