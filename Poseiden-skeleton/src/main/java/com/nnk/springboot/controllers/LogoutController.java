package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling user logout.
 */
@Controller
public class LogoutController {

    /**
     * Displays the logout success page after the user logs out.
     *
     * @return the logout view
     */
    @GetMapping("/logout-success")
    public ModelAndView logoutSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("logout");
        return mav;
    }
}
