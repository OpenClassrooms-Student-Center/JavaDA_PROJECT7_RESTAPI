package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {
    private UserService userService;


    private UserRepository userRepository;

    public LoginController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }

    /*@GetMapping("/login")
    public ModelAndView login() {
        System.out.println("in modelAndView, method to display login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }*/
    @GetMapping("/home")
    public String displayHome(){
        return "home";
    }


    //formulaire géré par sécurité
    //@PostMapping("/login")
    //public String

    /*@GetMapping("/login")
    public String displaylogin(){
        return"login";
    }*/
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }
    //@GetMapping("/user/add")
    //public String displayUserAddForm(){
    //    return"user/add";
    //}



    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
