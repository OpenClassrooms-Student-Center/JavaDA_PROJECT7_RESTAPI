package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RatingController {
    // TODO DONE: Inject Rating services
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        // TODO DONE: find all Rating, add to model
        List<Rating> rating = ratingService.findAllRating();
        model.addAttribute("rating",rating);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO DONE: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            ratingService.saveRating(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            return "rating/add";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        if (!ratingService.checkIfIdExists(id)) {
            return "redirect:/rating/list";
        }
        Rating rating = ratingService.findByRatingId(id);
        model.addAttribute("rating",rating);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO DONE: check required fields, if valid call services to update Rating and return Rating list
        if (!ratingService.checkIfIdExists(id)) {
            return "redirect:/rating/list";
        }
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            ratingService.saveRating(rating);
            return "redirect:/rating/list";
        }catch (Exception e) {
            return "rating/add";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: Find Rating by Id and delete the Rating, return to Rating list
        if (!ratingService.checkIfIdExists(id)) {
            return "redirect:/rating/list";
        }
        Rating rating = ratingService.findByRatingId(id);
        ratingService.deleteRating(rating);
        model.addAttribute("rating", ratingService.findAllRating());

        return "redirect:/rating/list";
    }
}
