package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
public class TradeController {

	@Autowired
	private TradeService tradeService;
	
    @RequestMapping("/trade/list")
    public String home(Model model) {
    	model.addAttribute("tradeList", tradeService.findAll());
    	return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
    	model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
        	return "trade/add";
        } else {
        tradeService.createTrade(trade);
        return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			return "trade/update";
		} else {
			try {
				tradeService.updateTrade(trade, id);
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
			}
			return "redirect:/trade/list";
		}
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    	tradeService.deleteById(id);
    	return "redirect:/trade/list";
    }
}
