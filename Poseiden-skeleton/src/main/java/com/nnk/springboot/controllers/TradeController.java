package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class TradeController {
    // TODO DONE: Inject Trade services
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        // TODO DONE: find all Trade, add to model
        List<Trade> trade = tradeService.findAllTrade();
        model.addAttribute("trade",trade);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO DONE: check data valid and save to db, after saving return Trade list
        if (result.hasErrors()) {
            return "trade/add";
        }
        try {
            tradeService.saveTrade(trade);
            return "redirect:/trade/list";
        } catch (Exception e) {
            return "trade/add";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: get Trade by Id and to model then show to the form
        if (!tradeService.checkIfIdExists(id)) {
            return "redirect:/trade/list";
        }
        Trade trade = tradeService.findTradeById(id);
        model.addAttribute("trade",trade);

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call services to update Trade and return Trade list
        if (!tradeService.checkIfIdExists(id)) {
            return "redirect:/trade/list";
        }
        if (result.hasErrors()) {
            return "trade/add";
        }
        try {
            tradeService.saveTrade(trade);
            return "redirect:/trade/list";
        }catch (Exception e) {
            return "trade/add";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: Find Trade by Id and delete the Trade, return to Trade list
        if (!tradeService.checkIfIdExists(id)) {
            return "redirect:/trade/list";
        }
        Trade trade = tradeService.findTradeById(id);
        tradeService.deleteTrade(trade);
        model.addAttribute("ruleName", tradeService.findAllTrade());

        return "redirect:/trade/list";
    }
}
