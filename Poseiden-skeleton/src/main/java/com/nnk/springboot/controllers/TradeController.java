package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class TradeController {
	@Autowired
    private TradeService tradeService;

    /**
	 * displays Trade list retrieved from database
	 */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAllTrades());
        return "trade/list";
    }

    /**
	 * displays form for Trade to add
	 */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    /**
	 * validates new Trade
	 * 
	 * @param trade:    Trade to create and save
	 * @param result: form result to validate
	 * @return Trade list displayed if validated, new add form with errors
	 *         displayed else
	 */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(!result.hasErrors()) {
        	tradeService.saveTrade(trade);
        	model.addAttribute("trades", tradeService.findAllTrades());
        	return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
	 * displays Trade form to update
	 * 
	 * @param id : id of the Trade to update
	 * @return return Trade form to update
	 */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
	 * validates updated Trade
	 * 
	 * @param id      : id of the Trade to update
	 * @param trade : Trade to validate
	 * @param result  : form result to validate
	 * @return Trade list with this Trade updated, Trade to update form
	 *         with errors displayed else
	 */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
        	return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.findAllTrades());
        return "redirect:/trade/list";
    }

    /**
	 * deletes selected Trade from database
	 * 
	 * @param id: id of the Trade to delete
	 * @return the Trade list after selected Trade deleted
	 */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.deleteTrade(trade);
        model.addAttribute("trades", tradeService.findAllTrades());
        return "redirect:/trade/list";
    }
}
