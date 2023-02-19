package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * The type Rating controller.
 */
@Controller
@Slf4j
public class RatingController {
    private final RatingService ratingService;

    /**
     * Instantiates a new Rating controller.
     *
     * @param ratingService the rating service
     */
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Home page to list all items.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratingList", ratingService.findAll());
        log.info("Displayed list of rating items");
        return "rating/list";
    }

    /**
     * Add rating form.
     *
     * @param rating the rating
     * @return the string
     */
    @GetMapping("/rating/add")
    public String addRatingForm(RatingDto rating) {
        return "rating/add";
    }

    /**
     * Validate.
     *
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDto rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation failed for rating item");
            return "rating/add";
        }
        ratingService.create(rating);
        log.info("Successfully created rating");
        return "redirect:/rating/list";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.findById(id));
        return "rating/update";
    }

    /**
     * Update rating.
     *
     * @param id     the id
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for rating item");
            return "rating/update";
        }
        ratingService.update(id, rating);
        log.info("Successfully updated rating with ID {}", id);
        return "redirect:/rating/list";
    }

    /**
     * Delete rating.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /rating/delete endpoint with id {}", id);
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
