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

import jakarta.validation.Valid;

@Controller
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * This method displays the list of all ratings in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * This method displays a form to add a new rating to the database<br>
     * The rating parameter will be overwritten with the form's values
     * @param rating an empty rating object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * This method adds a rating to the database, after checking its validity<br>
     * Upon resolution it redirects to rating/list
     * @param rating a rating object containing the rating to be added to the database
     * @param result the result of the rating's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.save(rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
     * This method displays information about a specific rating<br>
     * The displayed information can be modified
     * @param id the id of the rating to display
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid rating Id:" + id)));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * This method updates a rating in the database, after encrypting its password<br>
     * Upon resolution it redirects to rating/list
     * @param id the id of the rating to update
     * @param rating the new rating attributes
     * @param result the result of the new rating validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                              BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ratingService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            ratingService.save(rating);
            return "redirect:/rating/list";
        }
        return "redirect:/rating/update/{id}";
    }

    /**
     * This method deletes a rating from the database<br>
     * Upon resolution it redirects to rating/list
     * @param id the id of the rating to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
