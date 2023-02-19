package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * The type User controller.
 */
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Home page to list all users.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        log.info("Displayed list of users");
        return "user/list";
    }

    /**
     * Add user.
     *
     * @param user the user
     * @return the string
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Validate.
     *
     * @param user   the user
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            log.info("Successfully created user");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.create(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        log.error("Validation failed for user");
        return "user/add";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Update user.
     *
     * @param id     the id
     * @param user   the user
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for user");
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.create(user);
        log.info("Successfully updated user with ID {}", id);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Delete user.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /user/delete endpoint with id {}", id);
        userService.delete(id);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}