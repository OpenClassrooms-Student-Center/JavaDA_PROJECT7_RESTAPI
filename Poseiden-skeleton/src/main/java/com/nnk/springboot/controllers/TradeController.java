package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.TradeService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TradeController
 *
 * The controller for the trade operations.
 * Injects the Trade service.
 */
@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    /**
     * The trade service.
     */
    private final TradeService tradeService;

    /**
     * The trade list page.
     *
     * @param model the model
     * @param user the user
     * @return the trade list page
     */
    @GetMapping("/list")
    public String home(Model model, @AuthenticationPrincipal User user) {
        // find all Trade, add to model
        model.addAttribute("remoteUser", user.getUsername());
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * The add trade page.
     *
     * @param bid the bid
     * @return the add trade page
     */
    @GetMapping("/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    /**
     * Validate the trade.
     *
     * @param trade the trade
     * @param result the result
     * @param model the model
     * @return the add trade page
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Trade list
        if (!result.hasErrors()) {
            tradeService.save(trade);
        }
        return "trade/add";
    }

    /**
     * The update trade page.
     *
     * @param id the trade id
     * @param model the model
     * @return the update trade page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Trade by Id and to model then show to the form
        try {
            model.addAttribute("trade", tradeService.findById(id));
        } catch (RequestedObjectNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
        return "trade/update";
    }

    /**
     * Update the trade.
     *
     * @param id the trade id
     * @param trade the trade
     * @param result the result
     * @param model the model
     * @return the trade list page
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        // check required fields, if valid call service to update Trade and return Trade list
        if (!result.hasErrors()) {
            trade.setTradeid(id);
            tradeService.save(trade);
        }
        return "redirect:/trade/list";
    }

    /**
     * Delete the trade.
     *
     * @param id the trade id
     * @param model the model
     * @return the trade list page
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // Find Trade by Id and delete the Trade, return to Trade list
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}

