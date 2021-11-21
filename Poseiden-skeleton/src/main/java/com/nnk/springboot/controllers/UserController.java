package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            user.setPassword(encoder.encode(user.getPassword()));
            List<User> users = userService.saveOrUpdate(user);
            model.addAttribute("users", users);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        List<User> users = userService.saveOrUpdate(user);
        model.addAttribute("users", users);
        return "redirect:/user/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        List<User> users = userService.delete(id);
        model.addAttribute("users", users);
        return "redirect:/user/list";
    }
}
