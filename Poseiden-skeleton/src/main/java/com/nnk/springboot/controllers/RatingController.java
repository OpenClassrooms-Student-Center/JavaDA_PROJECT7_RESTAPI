package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller to manage Rating entities.
 */
@Controller
@RequestMapping("/rating")
public class RatingController {


    private final RatingService ratingService;

    /**
     * Constructor injection for RatingService.
     *
     * @param ratingService the rating service
     */
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Displays the list of ratings.
     *
     * @param model the model
     * @return the rating list view
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.getAllRating());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername() );
        return "rating/list";
    }

    /**
     * Displays the form to add a new rating.
     *
     * @param rating a new rating object
     * @return the add rating view
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates and saves a new rating.
     *
     * @param rating the rating object
     * @param result validation result
     * @param model  the model
     * @return the redirect path
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "rating/add";
        }
        ratingService.saveRating(rating);
        model.addAttribute("ratings", ratingService.getAllRating());
        return "redirect:/rating/list";
    }

    /**
     * Displays the form to update an existing rating.
     *
     * @param id    the rating id
     * @param model the model
     * @return the update rating view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRatingById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Updates an existing rating.
     *
     * @param id     the rating id
     * @param rating the updated rating object
     * @param result validation result
     * @param model  the model
     * @return the redirect path
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "rating/update";
        }
        rating.setId(id);
        ratingService.saveRating(rating);
        model.addAttribute("ratings", ratingService.getAllRating());
        return "redirect:/rating/list";
    }

    /**
     * Deletes a rating.
     *
     * @param id    the rating id
     * @param model the model
     * @return the redirect path
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRatingById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Rating Id:" + id));
        ratingService.deleteRatingById(id);
        model.addAttribute("ratings", ratingService.getAllRating());
        return "redirect:/rating/list";
    }
}
