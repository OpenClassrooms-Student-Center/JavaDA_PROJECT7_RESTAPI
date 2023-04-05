package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RatingController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private RatingService ratingService;
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("listOfRatings", ratingService.findAll());
        // TODO: find all Rating, add to model
        return "rating/list";
    }
//DISPLAY ADD RATING FORM
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }
//ADD NEW RATING
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.validateNewRating(rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }
//DISPLAY UPDATE RATING FORM
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }
//UPDATE RATING
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating updatedRatingEntity,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            return "rating/list";
        }
        Rating updatedAndSavedRating = ratingService.updateRating(id, updatedRatingEntity );

        model.addAttribute("listOfRatings", ratingService.findAll());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
}
