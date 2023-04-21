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
    public String homeDisplayRatingList(Model model) {
        log.info("REQUEST /rating/list");
        model.addAttribute("listOfRatings", ratingService.findAll());
        log.info("attribute ratingList added to Model");
        // TODO: find all Rating, add to model
        return "rating/list";
    }
//DISPLAY ADD RATING FORM
    @GetMapping("/rating/add")
    public String displayAddRatingForm(Rating rating) {
        log.info("GET form /rating/add");
        return "rating/add";
    }
//CREATE NEW RATING
    @PostMapping("/rating/validate")
    public String validateRating(@Valid Rating rating, BindingResult result, Model model)  {
        // TODO: check data valid and save to db, after saving return Rating list
        log.info("POST /rating/validate");
        if (result.hasErrors()) {
            log.error("rating to add has errors");
            return "rating/add";
        }
        try{
            ratingService.validateNewRating(rating);
            log.info("rating validated with id " + rating.getRating_id());
        }catch(Exception e){
            log.error("rating could not be created" );
            return "rating/add";
        }
        return "redirect:/rating/list";
    }
//DISPLAY UPDATE RATING FORM
    @GetMapping("/rating/update/{id}")
    public String displayUpdateRatingForm(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /rating/update/{id} with id " + id);
            Rating rating = ratingService.getRatingById(id);
            model.addAttribute("rating", rating);
            log.info("attribute added to Model : rating with id " + rating.getRating_id());
            // TODO: get Rating by Id and to model then show to the form
            return "rating/update";
        }catch(Exception e){
            log.error("rating update form with id "+id+" could not be displayed");
            return "rating/list";
        }

    }
//UPDATE RATING
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating updatedRatingEntity,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        log.info("POST /rating/update/{id} with id " + id);


        try {
            if (result.hasErrors()) {
                log.error("rating to update has errors");
                throw new Exception();
            }
            Rating updatedAndSavedRating = ratingService.updateRating(id, updatedRatingEntity);
            model.addAttribute("listOfRatings", ratingService.findAll());
            log.info("listOfRatings added to model");

        } catch (Exception e) {
            log.error("rating with id " + id + " could not be update");
            return "redirect:/rating/update/"+id+"";
        }
        return "redirect:/rating/list";
    }
//DELETE RATING
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /rating/delete/{id} with id " + id);
            ratingService.deleteRating(id);
            // TODO: Find Rating by Id and delete the Rating, return to Rating list
            log.info("rating with id " + id + "deleted");
            return "redirect:/rating/list";
        }catch(Exception e){
            log.error("rating with id "+id+" could not be deleted");
            return "rating/list";
        }
    }
}
