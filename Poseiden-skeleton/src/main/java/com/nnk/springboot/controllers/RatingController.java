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

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
	 * displays Rating list retrieved from database
	 */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "rating/list";
    }

    /**
	 * displays form for new Rating to add
	 */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
	 * validates new Rating
	 * 
	 * @param rating:    Rating to create and save
	 * @param result: form result to validate
	 * @return Rating list displayed if validated, new add form with errors
	 *         displayed else
	 */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(!result.hasErrors()) {
        	ratingService.saveRating(rating);
        	model.addAttribute("ratings", ratingService.findAllRatings());
        	return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
	 * displays Rating form to update
	 * 
	 * @param id : id of the Rating to update
	 * @return return Rating form to update
	 */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }


	/**
	 * validates updated Rating
	 * 
	 * @param id      : id of the Rating to update
	 * @param rating : Rating to validate
	 * @param result  : form result to validate
	 * @return Rating list with this Rating updated, Rating to update form
	 *         with errors displayed else
	 */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
        	return "/rating/update";
        }
        rating.setId(id);
        ratingService.saveRating(rating);
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "redirect:/rating/list";
    }

    /**
	 * deletes selected Rating from database
	 * 
	 * @param id: id of the Rating to delete
	 * @return the Rating list after selected Rating deleted
	 */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	Rating rating = ratingService.findRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteRating(rating);
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "redirect:/rating/list";
    }
}
