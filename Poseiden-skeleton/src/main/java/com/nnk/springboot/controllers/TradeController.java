package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@Controller
public class TradeController {

	@Autowired
	private TradeService tradeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
	
    @RequestMapping("/trade/list")
    public String home(Model model) {
    	model.addAttribute("tradeList", tradeService.findAll());
		LOGGER.info("Trade List");
    	return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
    	model.addAttribute("trade", new Trade());
		LOGGER.info("Trade add form");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
        	return "trade/add";
        } else {
        tradeService.createTrade(trade);
		LOGGER.info("Trade added");
        return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id);
		LOGGER.info("Trade to update found");
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "trade/update";
		} else {
			try {
				tradeService.updateTrade(trade, id);
				LOGGER.info("Trade updated");
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This Trade doesn't exist");
			}
			return "redirect:/trade/list";
		}
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		LOGGER.info("Deleting Trade with id:" + id);
    	tradeService.deleteById(id);
    	return "redirect:/trade/list";
    }
}
