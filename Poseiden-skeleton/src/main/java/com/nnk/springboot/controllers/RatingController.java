package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.RatingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RatingController class
 */
@Controller
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    /**
     * Rating service
     */
    private final RatingService ratingService;

    /**
     * list all Ratings
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/list")
    public String home(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("remoteUser", user.getUsername());
        model.addAttribute("ratings", ratingService.getRatings());
        return "rating/list";
    }

    /**
     * show add new form
     * @return
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * validate input data and save to db
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.getRatings());
        }
        return "rating/add";
    }

    /**
     * show update form
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("rating", ratingService.getRatingById(id));
        } catch (RequestedObjectNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
        return "rating/update";
    }

    /**
     * update rating
     * @param id
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(!result.hasErrors()) {
            rating.setRatingid(id);
            ratingService.saveRating(rating);
        }
        return "redirect:/rating/list";
    }

    /**
     * delete rating
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}

