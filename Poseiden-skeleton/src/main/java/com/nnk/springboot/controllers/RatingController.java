package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@Controller
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratingList", ratingService.findAll());
		LOGGER.info("Rating List");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
    	model.addAttribute("rating", new Rating());
		LOGGER.info("Rating add form");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
        	return "rating/add";
        } else {
        ratingService.createRating(rating);
		LOGGER.info("Rating added");
        return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Rating rating = ratingService.findById(id);
		LOGGER.info("Rating to update found");
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "rating/update";
		} else {
			try {
				ratingService.updateRating(rating, id);
				LOGGER.info("Rating updated");
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This rating doesn't exist");
			}
			return "redirect:/rating/list";
		}
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
		LOGGER.info("Deleting Rating with id:" + id);
    	ratingService.deleteById(id);
    	return "redirect:/rating/list";
    }
}
