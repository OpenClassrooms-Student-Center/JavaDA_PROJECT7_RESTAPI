package com.nnk.springboot.controllers;

import com.nnk.springboot.service.IUserService;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {


    private IUserService userService;

    public RegistrationController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * @passwordEncoder cryptage password
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){

        return new UserRegistrationDto();
    }

    /**
     * endpoint to get show form add contact
     * @return registration page
     */
    @GetMapping
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("registration");
    }

    /**
     * @param registrationDto firstName, lastName, email, password
     * endpoint to post parameter new contact
     * @return registration?success page
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        String cryptedPassword = passwordEncoder.encode(registrationDto.getPassword());
        userService.saveUser(registrationDto, cryptedPassword);
        return "redirect:/registration?success";
    }

}
