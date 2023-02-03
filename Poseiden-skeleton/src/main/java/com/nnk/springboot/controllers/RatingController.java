package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IRatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RatingController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("RatingController");

    /**
     * IBidListService instance.
     */
    private IRatingService ratingService;

    /**
     * @param ratingService
     */
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }


    /**
     * @param model
     * @return "rating/list"
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.info("@RequestMapping(\"/rating/list\")");
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * @param rating
     * @return "rating/add"
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("@RequestMapping(\"/rating/add\")");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("@PostMapping(\"/rating/validate\")");
        /**form data validation*/
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    /**
     * @param id
     * @param model
     * @return "rating/update" form
     * @throws DataNotFoundException
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/rating/update/{id}\")");
        Optional<Rating> rating = ratingService.findById(id);
        if(rating.isPresent() ){
            model.addAttribute("Error", "This " + rating.get() + " is present");
        }
        model.addAttribute("rating",rating.get());
        return "rating/update";
    }

    /**
     * @param id
     * @param rating
     * @param result
     * @param model
     * @return rating update "redirect:/rating/list"
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) throws DataNotFoundException {
        logger.info("@PostMapping(\"/rating/update/{id}\")");
        if (result.hasErrors()) {
            logger.error("result error :{}", result.getFieldError());
            return "rating/update";
        }
        rating.setId(id);
        ratingService.save(rating);
        model.addAttribute("rating", ratingService.findAll());
        return "redirect:/rating/list";
    }


    /**
     * @param id
     * @param model
     * @return delete rating
     * @throws DataNotFoundException
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/rating/delete/{id}\"");

        ratingService.delete(id);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}
