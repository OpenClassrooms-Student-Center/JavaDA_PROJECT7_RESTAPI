package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * The type Trade controller.
 */
@Controller
@Slf4j
public class TradeController {
    private final TradeService tradeService;

    /**
     * Instantiates a new Trade controller.
     *
     * @param tradeService the trade service
     */
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Home page to list all items.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("tradeList", tradeService.findAll());
        log.info("Displayed list of trade items");
        return "trade/list";
    }

    /**
     * Add trade.
     *
     * @param trade the trade
     * @return the string
     */
    @GetMapping("/trade/add")
    public String addTrade(TradeDto trade) {
        return "trade/add";
    }

    /**
     * Validate.
     *
     * @param trade  the trade
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDto trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation failed for trade item");
            return "trade/add";
        }
        tradeService.create(trade);
        log.info("Successfully created trade");
        return "redirect:/trade/list";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.findById(id));
        return "trade/update";
    }

    /**
     * Update trade.
     *
     * @param id     the id
     * @param trade  the trade
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDto trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for update item");
            return "trade/update";
        }
        tradeService.update(id, trade);
        log.info("Successfully updated trade with ID {}", id);
        return "redirect:/trade/list";
    }

    /**
     * Delete trade.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /trade/delete endpoint with id {}", id);
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}
