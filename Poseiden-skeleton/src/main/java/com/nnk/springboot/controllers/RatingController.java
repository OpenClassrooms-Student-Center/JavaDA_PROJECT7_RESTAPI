package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Rating controller.
 */
@Controller
@AllArgsConstructor
public class RatingController {
    private static Logger logger = LoggerFactory.getLogger(RatingController.class);
    private final RatingService ratingService;

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/rating/list")
    @PreAuthorize("hasRole('USER')")
    public String home(Model model)
    {
        logger.info("get list");
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Add rating form string.
     *
     * @param rating the rating
     * @return the string
     */
    @GetMapping("/rating/add")
    @PreAuthorize("hasRole('USER')")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validate string.
     *
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/validate")
    @PreAuthorize("hasRole('USER')")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("validate rating");
        if (!result.hasErrors()) {
            logger.info("add rating {}", rating);
            List<Rating> ratings = ratingService.saveOrUpdate(rating);
            logger.info("saved rating {}", ratings);
            model.addAttribute("ratings", ratings);
            return "redirect:/rating/list";
        }

        logger.error("rating validate error");
        return "rating/add";
    }

    /**
     * Show update form string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find rating by id {}", id);
        model.addAttribute("rating", ratingService.findById(id));
        return "rating/update";
    }

    /**
     * Update rating string.
     *
     * @param id     the id
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        logger.info("update rating {}", rating);
        if (result.hasErrors()) {
            return "rating/update";
        }
        List<Rating> ratings = ratingService.saveOrUpdate(rating);
        model.addAttribute("ratings", ratings);
        return "redirect:/rating/list";
    }

    /**
     * Delete rating string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("delete rating by Id {}", id);
        List<Rating> ratings = ratingService.delete(id);
        model.addAttribute("ratings", ratings);
        return "redirect:/rating/list";
    }
}
