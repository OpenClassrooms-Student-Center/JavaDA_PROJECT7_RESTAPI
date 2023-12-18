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

import jakarta.validation.Valid;

@Controller
public class TradeController {
    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * This method displays the list of all trades in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * This method displays a form to add a new trade to the database<br>
     * The trade parameter will be overwritten with the form's values
     * @param trade an empty trade object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    /**
     * This method adds a trade to the database, after checking its validity<br>
     * Upon resolution it redirects to trade/list
     * @param trade a trade object containing the trade to be added to the database
     * @param result the result of the trade's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.save(trade);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * This method displays information about a specific trade<br>
     * The displayed information can be modified
     * @param id the id of the trade to display
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid trade Id:" + id)));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * This method updates a trade in the database, after encrypting its password<br>
     * Upon resolution it redirects to trade/list
     * @param id the id of the trade to update
     * @param trade the new trade attributes
     * @param result the result of the new trade validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(!result.hasErrors()) {
            tradeService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            tradeService.save(trade);
            return "redirect:/trade/list";
        }
        return "redirect:/trade/update/{id}";
    }

    /**
     * This method deletes a trade from the database<br>
     * Upon resolution it redirects to trade/list
     * @param id the id of the trade to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
