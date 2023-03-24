package com.poseidon.api.controllers;

import com.poseidon.api.model.User;
import com.poseidon.api.model.dto.UserDto;
import com.poseidon.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Show the users registered on the website
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    /**
     * Show the form to add an user
     *
     * @param userDto							UserDto with fields that are required to create a new user
     * @return									Add an user view
     */
    @GetMapping("/user/add")
    public String addUser(UserDto userDto) {
        return "user/add";

    }

    /**
     * Validate the fields of the "Add User" Form
     *
     * @param userDto							UserDto with filled fields that are going to be validated
     * @param result							The result of the validation
     * @return									Returns the list of users if the form was validated, otherwise show errors
     */
    @PostMapping("/user/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {
            User newUser = userService.convertDtoToEntity(userDto);

            try {
                userService.createUser(newUser);
            } catch (UsernameNotFoundException error) {
                result.rejectValue("username", "", "Username is already taken");
                return "user/add";
            }

            redirectAttributes.addFlashAttribute("message",
                    String.format("User with username '%s' and role '%s' was successfully created",
                            newUser.getUsername(), newUser.getRole()));

            model.addAttribute("users", userService.findAllUsers());

            return "redirect:/user/list";
        }

        return "user/add";
    }

    /**
     * Show the form to update an existing user
     *
     * @param id
     * @return								    The form to update an user
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User userToUpdate = userService.findUserById(id);
            UserDto user = userService.convertEntityToDto(userToUpdate);
            user.setPassword("");
            model.addAttribute("userDto", user);
        } catch (UsernameNotFoundException error) {
            redirectAttributes.addFlashAttribute("message", error.getMessage());
//            redirectAttributes.addFlashAttribute("message_type", BootstrapAlerts.WARNING);
            return "redirect:/user/list";
        }

        return "user/update";
    }

    /**
     * Validate the fields for the update of user
     *
     * @param id
     * @param userDto
     * @return									Returns the list of users if the form was validated, otherwise show errors
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid UserDto userDto, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "user/update";
        }

        User newUser = userService.convertDtoToEntity(userDto);
        userService.updateUser(id, newUser);

        redirectAttributes.addFlashAttribute("message",
                String.format("User '%s' was successfully updated", newUser.getUsername()));
//        redirectAttributes.addFlashAttribute("message_type", BootstrapAlerts.PRIMARY);

        model.addAttribute("users", userService.findAllUsers());

        return "redirect:/user/list";
    }

    /**
     * Delete a user by its ID
     *
     * @param id
     * @return									The users list if the deletion was successful
     *
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        String username = "";
        try {
            username = userService.findUserById(id).getUsername();
            userService.deleteUser(id);
        } catch (UsernameNotFoundException error) {
            redirectAttributes.addFlashAttribute("message", error.getMessage());
            return "redirect:/user/list";
        }

        redirectAttributes.addFlashAttribute("message", String.format("User '%s' was successfully deleted", username));

        model.addAttribute("users", userService.findAllUsers());

        return "redirect:/user/list";
    }


}
