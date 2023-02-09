package com.nnk.springboot.controllers;

import com.nnk.springboot.model.User;
import com.nnk.springboot.model.dto.UserRegistration;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }


    @GetMapping("/user/add")
    public String addUser(UserRegistration userRegistration) {
        return "user/add";

    }


}
