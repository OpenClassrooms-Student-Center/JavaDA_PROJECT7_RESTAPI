package com.nnk.springboot.controllers;

import com.nnk.springboot.config.security.JwtTokenUtil;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.JwtService;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Rating controller.
 */
@RestController
@AllArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public String createJwtToken(@RequestBody User user) throws Exception {
        return jwtService.authenticate(user);
    }
}
