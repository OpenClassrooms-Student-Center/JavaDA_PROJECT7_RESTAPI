package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class RatingController {
    // TODO: Inject Rating service
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if(result.getErrorCount() == 1
                && Arrays.stream(result.getFieldError().getCodes()).filter(p -> p.equals("NotNull.id")).count() == 1) {
            ratingService.save(rating);
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Optional<Rating> rating = ratingService.findById(id);
        if(rating.isPresent()) {
            model.addAttribute("rating", rating.get());
        }
        else {
            model.addAttribute("rating", new Rating());
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if(result.getErrorCount() == 0
                && ratingService.findById(id).isPresent()) {
            ratingService.save(rating);
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        Optional<Rating> ratingInDB = ratingService.findById(id);
        if(ratingInDB.isPresent()) {
            ratingService.deleteById(id);
        }
        return "redirect:/rating/list";
    }
}
