package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserCustom;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("request", request);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(UserCustom user, HttpServletRequest request) {return "user/add";}

    @PostMapping("/user/validate")
    public String validate(@Valid UserCustom user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserCustom userCustom = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userCustom.setPassword("");
        model.addAttribute("userCustom", userCustom);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserCustom userCustom,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userCustom.setPassword(encoder.encode(userCustom.getPassword()));
        userService.saveUser(userCustom);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        UserCustom user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
