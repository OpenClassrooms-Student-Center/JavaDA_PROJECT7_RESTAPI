package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.LoggerApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingController {

    private static final String REDIRECTRATINGLIST = "redirect:/rating/list";
    private static final String RATINGS = "ratings";

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private LoggerApi loggerApi;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(RATINGS, ratingRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, HttpServletRequest request,
            HttpServletResponse response) {

        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute(RATINGS, ratingRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTRATINGLIST;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("rating", rating);

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
            BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "rating/update";
        }

        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("users", ratingRepository.findAll());
        response.setStatus(HttpServletResponse.SC_CREATED); // response 201

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTRATINGLIST;
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute(RATINGS, ratingRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTRATINGLIST;
    }
}
