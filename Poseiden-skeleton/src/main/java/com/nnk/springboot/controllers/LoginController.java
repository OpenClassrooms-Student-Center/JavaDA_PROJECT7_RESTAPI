package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String errorMessage;
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "You are not authorized for the requested data.";
                mav.addObject("errorMsg", errorMessage);
                mav.setViewName("403");
                return mav;
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "The requested resource cannot be found.";
                mav.addObject("errorMsg", errorMessage);
                mav.setViewName("404");
                return mav;
            }
        }

        errorMessage = "Something went wrong.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error");
        return mav;
    }
}
