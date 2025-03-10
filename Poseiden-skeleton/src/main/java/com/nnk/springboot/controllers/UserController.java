package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to manage User entities.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor-based dependency injection.
     *
     * @param userService the user service to handle user-related operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the list of all users.
     *
     * @param model the model to pass attributes to the view
     * @return the view name for displaying the list of users
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        return "user/list";
    }

    /**
     * Displays the form to add a new user.
     *
     * @return the add user view
     */
    @GetMapping("/add")
    public String addUserForm(User user) {
        return "user/add";
    }

    /**
     * Validates and saves a new user.
     * If there are validation errors, the user will be shown the add form again.
     *
     * @param user the user object populated with form data
     * @param result the binding result that contains validation errors, if any
     * @param model the model to pass attributes to the view
     * @return the redirect path to the user list if the user is saved, or the add form if there are errors
     */
    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.saveUser(user);
            model.addAttribute("users", userService.getAllUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Displays the form to update an existing user.
     *
     * @param id the id of the user to be updated
     * @param model the model to pass the user data to the view
     * @return the update user view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");  // Clear password to not show the current password
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Validates and updates an existing user.
     * If there are validation errors, the user will be shown the update form again.
     *
     * @param id the id of the user to be updated
     * @param user the updated user object
     * @param result the binding result that contains validation errors, if any
     * @param model the model to pass attributes to the view
     * @return the redirect path to the user list if the user is updated, or the update form if there are errors
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        user.setId(id);
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the id of the user to be deleted
     * @param model the model to pass attributes to the view
     * @return the redirect path to the user list after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }
}
