package com.nnk.springboot.controllers;

import java.text.ParseException;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.ValidInput;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class BidListController {

    private static final String REDIRECTBIDLIST = "redirect:/bidList/list";
    private static final String BIDS = "bids";
    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(BidListController.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private ValidInput validInput;

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(BIDS, bidListRepository.findAll());

        model.addAttribute("httpServletRequest", request);

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, HttpServletResponse response,
            @RequestParam String bidListDateString, @RequestParam String creationDateString,
            @RequestParam String revisionDateString, HttpServletRequest request) throws ParseException {

        if (!result.hasErrors()) {

            validInput.addBid(bid, bidListDateString, creationDateString, revisionDateString);

            if (validInput.getAddBid()) {

                model.addAttribute(BIDS, bidListRepository.findAll());
                response.setStatus(HttpServletResponse.SC_CREATED); // response 201

                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
                LOGGER.info(messageLoggerInfo);

                return REDIRECTBIDLIST;

            } else {
                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Bid not added!");
                LOGGER.info(messageLoggerInfo);
                return "bidList/add";
            }

        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("bidList", bidList);

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model, HttpServletResponse response,
            @RequestParam String bidListDateString, @RequestParam String creationDateString,
            @RequestParam String revisionDateString, HttpServletRequest request) throws ParseException {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "bidList/update";
        }

        validInput.updateBid(bidList, id, bidListDateString, creationDateString, revisionDateString);

        if (validInput.getUpdateBid()) {
            model.addAttribute("bidList", bidListRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTBIDLIST;
        } else {
            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Bid not updated!");
            LOGGER.info(messageLoggerInfo);
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute(BIDS, bidListRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTBIDLIST;
    }

}
