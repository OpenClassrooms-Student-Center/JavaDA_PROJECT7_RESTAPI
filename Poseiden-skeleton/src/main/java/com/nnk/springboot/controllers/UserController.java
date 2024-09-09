package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;

import com.nnk.springboot.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * This class represents a controller for handling user related requests.
 * It provides methods for listing all users, adding a new user, showing the update form,
 * updating a user, deleting a user and displaying the login page.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService= userService;
    }
    /**
     * This method handles the request to display a list of all users.
     * It adds the list of users to the model and returns the string "user/list".
     *
     * @param model The model to add the list of users to.
     * @return The string "user/list".
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * This method handles the request to display the form for adding a new user.
     * It returns the string "user/add".
     *
     * @return The string "user/add".
     */
    @GetMapping("/add")
    public String addUser(User bid) {
        return "user/add";
    }

    /**
     * This method handles the request to validate a user.
     * If the user is valid, it saves the user and returns the string "user/add".
     *
     * @param user The user to validate.
     * @param result The result of the validation.
     * @param model The model to add error messages to.
     * @return The string "user/add".
     */
    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) { 
            userService.save(user);
        }
        return "user/add";
    }

    /**
     * This method handles the request to display the form for updating a user.
     * It finds the user with the given ID and adds it to the model. If the user is not found,
     * it adds an error message to the model and returns the string "404".
     *
     * @param id The ID of the user to update.
     * @param model The model to add the user to or the error message to.
     * @return The string "user/update" or "404".
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            model.addAttribute("user", userService.findById(id));
            return "user/update";
        } catch (RequestedObjectNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
    }

    /**
     * This method handles the request to update a user.
     * If the user is valid, it updates the user and returns the string "redirect:/user/list".
     *
     * @param id The ID of the user to update.
     * @param user The user to update.
     * @param result The result of the validation.
     * @param model The model to add the list of users to.
     * @return The string "redirect:/user/list".
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        user.setUserid(id);
        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * This method handles the request to delete a user.
     * It deletes the user with the given ID and returns the string "redirect:/user/list".
     *
     * @param id The ID of the user to delete.
     * @param model The model to add the list of users to.
     * @return The string "redirect:/user/list".
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
    /**
     * This method handles the request to display the login page.
     * It returns the string "loginPage".
     *
     * @return The string "loginPage".
     */
    @GetMapping("/loginPage")
    public String getMethodName() {
        return "loginPage";
    }
    
}

