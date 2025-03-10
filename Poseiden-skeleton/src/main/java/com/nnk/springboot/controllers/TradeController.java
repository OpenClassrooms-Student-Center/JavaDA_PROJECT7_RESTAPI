package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller to manage Trade entities.
 */
@Controller
@RequestMapping("/trade")
public class TradeController {


    private final TradeService tradeService;

    /**
     * Constructor-based dependency injection.
     *
     * @param tradeService the service to manage trade entities
     */
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Displays the list of all trades.
     *
     * @param model the model to pass attributes to the view
     * @return the view name for displaying the list of trades
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAllTrades());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername() );
        return "trade/list";
    }

    /**
     * Displays the form to add a new trade.
     *
     * @return the add trade view
     */
    @GetMapping("/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    /**
     * Validates and saves a new trade.
     * If there are validation errors, the user will be shown the add form again.
     *
     * @param trade the trade object populated with form data
     * @param result the binding result that contains validation errors, if any
     * @param model the model to pass attributes to the view
     * @return the redirect path to the trade list if the trade is saved, or the add form if there are errors
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "trade/add";
        }
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }

    /**
     * Displays the form to update an existing trade.
     *
     * @param id the id of the trade to be updated
     * @param model the model to pass the trade data to the view
     * @return the update trade view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTradeById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Trade Id: " + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Validates and updates an existing trade.
     * If there are validation errors, the user will be shown the update form again.
     *
     * @param id the id of the trade to be updated
     * @param trade the updated trade object
     * @param result the binding result that contains validation errors, if any
     * @param model the model to pass attributes to the view
     * @return the redirect path to the trade list if the trade is updated, or the update form if there are errors
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }

    /**
     * Deletes a trade by its id.
     *
     * @param id the id of the trade to be deleted
     * @param model the model to pass attributes to the view
     * @return the redirect path to the trade list after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTradeById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Trade Id: " + id));
        tradeService.deleteTradeById(id);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }
}
