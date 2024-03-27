package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RatingController {
    private RatingService ratingService;
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("ratings", ratingService.findAll());
        model.addAttribute("request", request);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) { //check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            return "redirect:list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) { // get Rating by Id and to model then show to the form
        model.addAttribute("rating", ratingService.findRatingById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) { // check required fields, if valid call service to update Rating and return Rating list
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {//  Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRating(ratingService.findRatingById(id));
        return "redirect:/rating/list";
    }
}
