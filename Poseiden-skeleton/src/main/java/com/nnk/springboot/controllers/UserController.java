package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * This method displays the list of all users in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * This method displays a form to add a new user to the database<br>
     * The user parameter will be overwritten with the form's values
     * @param user an empty user object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * This method adds a user to the database, after encrypting its password
     * @param user a user object containing the user to be added to the database
     * @param result the result of the user's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors() && user.validatePassword()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * This method displays information about a specific user<br>
     * It also checks that the user making the request is allowed to do so. Any user can update its own profile,
     * admins can update all user profiles<br>
     * The displayed information can be modified
     * @param id the id of the user to display
     * @param model
     * @param authentication
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if(user.getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("user", user);
            return "user/update";
        }
        else {
            return "error/403";
        }
    }

    /**
     * This method updates a user in the database, after encrypting its password<br>
     * It also checks that the user making the request is allowed to do so. Any user can update its own profile,
     * admins can update all user profiles
     * @param id the id of the user to update
     * @param user the new user attributes
     * @param result the result of the new user validation
     * @param model
     * @param authentication
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors() || !user.validatePassword() || userService.findById(id).isEmpty()) {
            return "redirect:/user/update/{id}";
        }
        if(user.getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        else {
            return "error/403";
        }
    }

    /**
     * This method deletes a user from the database
     * @param id the id of the user to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
