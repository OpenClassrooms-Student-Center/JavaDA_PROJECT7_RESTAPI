package com.nnk.springboot.controllers;

import com.nnk.springboot.interfaces.RatingService;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Controller
public class RatingController {

    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        log.info("home: show rating/list");
        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm() {
        log.info("addRatingForm: show rating/add"  );
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating) {
        log.info("validate: FitchRating; " + rating.getFitchRating()
                + " MoodysRating; " + rating.getMoodysRating()
                + " SandPRating; " + rating.getSandPRating()
                + "OrderNumber" + rating.getOrderNumber());

        ratingService.validateRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("showUpdateForm: Rating  " + id);
        model.addAttribute("rating", ratingRepository.findRatingById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("updateRating: " + id);
        if(result.hasErrors()){
            redirectAttributes.addAttribute("error", true);
        }
        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        log.info("deleteRating:" + id);
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
