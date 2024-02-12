package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.ValidInput;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UserController {

    private static final String REDIRECTUSERLIST = "redirect:/user/list";
    private static final String USERS = "users";

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ValidInput validInput;

    @Secured("ADMIN")
    @RequestMapping("/user/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(USERS, userRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "user/list";
    }

    @Secured("ADMIN")
    @GetMapping("/user/add")
    public String addUser(Users bid, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "user/add";
    }

    @Secured("ADMIN")
    @PostMapping("/user/validate")
    public String validate(@Valid Users user, BindingResult result, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        if (!result.hasErrors()) {

            String wrongPassword = "";

            validInput.addUser(user);

            if (validInput.getAddUser()) {
                model.addAttribute(USERS, userRepository.findAll());
                response.setStatus(HttpServletResponse.SC_CREATED); // response 201

                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
                LOGGER.info(messageLoggerInfo);

                return REDIRECTUSERLIST;

            } else {
                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "User not added!");
                LOGGER.info(messageLoggerInfo);

                wrongPassword = "Password ! au moins une lettre majuscule, au moins 8 caractères, au moins un chiffre et un symbole";
                model.addAttribute("wrongPassword", wrongPassword);

                return "user/add";
            }

        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "user/add";
    }

    @Secured("ADMIN")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        users.setPassword("");
        model.addAttribute("users", users);

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "user/update";
    }

    @Secured("ADMIN")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid Users user,
            BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            model.addAttribute("user", user);

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "user/update";
        }

        validInput.pswValidInputs(user, id);

        if (validInput.getPswValidInput()) {
            model.addAttribute(USERS, userRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTUSERLIST;

        } else {
            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "User not updated!");
            LOGGER.info(messageLoggerInfo);
            return "user/update";
        }

    }

    @Secured("ADMIN")
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute(USERS, userRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTUSERLIST;
    }
}
