package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private TradeService tradeService;

    public TradeController(TradeService tradeService){
        this.tradeService=tradeService;
    }
    // TODO: Inject Trade service
//DISPLAY LIST OF TRADES
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("listOfTrades", tradeService.findAll());
        // TODO: find all Trade, add to model
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }


//CREATE NEW TRADE
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.validateNewTrade(trade);
            return "redirect:/trade/list";
        }
        // TODO: check data valid and save to db, after saving return Trade list
        return "trade/add";
    }
//DISPLAY TRADE UPDATE FORM
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        // TODO: get Trade by Id and to model then show to the form
        return "trade/update";
    }
//UPDATE TRADE
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade updatedTradeEntity,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/list";
        }
        Trade updatedAndSavedTrade = tradeService.updateTrade(id, updatedTradeEntity );

        model.addAttribute("listOfTrades", tradeService.findAll());
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }
//DELETE TRADE
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);

        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }
}
