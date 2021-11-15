package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class RatingController {
    private static Logger logger = LoggerFactory.getLogger(RatingController.class);
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        logger.info("get list");
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("validate curvePoint");
        if (!result.hasErrors()) {
            logger.info("add rating {}", rating);
            List<Rating> ratings = ratingService.saveOrUpdate(rating);
            logger.info("saved curvePoint {}", ratings);
            model.addAttribute("ratings", ratings);
            return "redirect:/rating/list";
        }

        logger.error("curvePoint validate error");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find rating by id {}", id);
        model.addAttribute("rating", ratingService.findById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
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

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("delete curvePoint by Id {}", id);
        List<Rating> ratings = ratingService.delete(id);
        model.addAttribute("ratings", ratings);
        return "redirect:/rating/list";
    }
}
