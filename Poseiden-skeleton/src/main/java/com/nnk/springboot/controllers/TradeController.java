package com.nnk.springboot.controllers;

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
//DISPLAY LIST OF TRADES PAGE
    @RequestMapping("/trade/list")
    public String homeDisplayTradesList(Model model) {
        log.info ("REQUEST /trade/list");
        model.addAttribute("listOfTrades", tradeService.findAll());
        // TODO: find all Trade, add to model
        log.info("attribute listOfTrades added to Model");
        return "trade/list";
    }
//DISPLAY ADD TRADE FORM
    @GetMapping("/trade/add")
    public String displayAddUserForm(Trade trade) {
        log.info("GET form /trade/add");
        return "trade/add";
    }
//CREATE NEW TRADE
    @PostMapping("/trade/validate")
    public String validateTrade(@Valid Trade trade, BindingResult result, Model model) {
        log.info("POST /trade/validate");
        if (result.hasErrors()) {
            log.error("trade to create has errors");
            return "trade/add";

        }
        try {
            tradeService.validateNewTrade(trade);
            log.info("trade validated with id " + trade.getTrade_id());

        } catch (Exception e){
            log.error("registration was not possible");
            return "trade/add";

            // TODO: check data valid and save to db, after saving return Trade list

        }
        return "redirect:/trade/list";
    }
//DISPLAY TRADE UPDATE FORM
    @GetMapping("/trade/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /trade/update/{id} with id "+ id);
            Trade trade = tradeService.getTradeById(id);
            model.addAttribute("trade", trade);
            // TODO: get Trade by Id and to model then show to the form
            log.info("attribute added to model : trade with id "+trade.getTrade_id());
            return "trade/update";
        }catch(Exception e){
            log.error("trade update form with id "+id+" could not be displayed");
            return "trade/list";
        }

    }
//UPDATE TRADE
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade updatedTradeEntity,
                             BindingResult result, Model model) {
        log.info("POST /trade/update{id} with id "+ id);

        try {
            if (result.hasErrors()) {
                log.error("trade to update has errors");
                throw new Exception();
            }
            Trade updatedAndSavedTrade = tradeService.updateTrade(id, updatedTradeEntity);
            model.addAttribute("listOfTrades", tradeService.findAll());
            // TODO: check required fields, if valid call service to update Trade and return Trade list
            log.info("attribute listOfTrades added to model");

        }catch(Exception e){
            log.error("trade with id "+id+" could not be updated");
            return "redirect:/trade/update/"+id+"";
        }
        return "redirect:/trade/list";
    }
//DELETE TRADE
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /trade/delete/{id} with id " + id);
            tradeService.deleteTrade(id);

            // TODO: Find Trade by Id and delete the Trade, return to Trade list
            log.info("trade with id " + id + " deleted");
            return "redirect:/trade/list";
        }catch(Exception e){
            log.error("trade with id "+ id + "could not be deleted");
            return ("trade/list");
        }
    }
}
