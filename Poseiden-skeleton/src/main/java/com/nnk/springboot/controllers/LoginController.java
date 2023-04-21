package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private UserService userService;


    private UserRepository userRepository;

    public LoginController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }
    //cette requete GET affiche la page login, sans vue.
    @GetMapping("/login")
    public ModelAndView login() {
        log.info("GET /login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        log.info("ModelAndView mav has name set to 'login'");
        return mav;
    }


    //cette requete fait le même travail que /user/list (mais alors elle est accessible???)
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("GET secure/article-details");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        log.info("Model And View mav has name set to 'user/list'");
        return mav;
    }

}
