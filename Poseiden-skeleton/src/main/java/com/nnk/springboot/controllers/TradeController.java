package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.RuleService;
import com.nnk.springboot.services.TradeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Trade controller.
 */
@Controller
@AllArgsConstructor
public class TradeController {
    private static Logger logger = LoggerFactory.getLogger(TradeController.class);
    private final TradeService tradeService;

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.info("get list");
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Add user string.
     *
     * @param trade the trade
     * @return the string
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * Validate string.
     *
     * @param trade  the trade
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("validate trade");
        if (!result.hasErrors()) {
            logger.info("add trade {}", trade);
            List<Trade> trades = tradeService.saveOrUpdate(trade);
            logger.info("saved trade {}", trades);
            model.addAttribute("trades", trades);
            return "redirect:/trade/list";
        }

        logger.error("trade validate error");
        return "trade/add";
    }

    /**
     * Show update form string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find trade by id {}", id);
        model.addAttribute("trade", tradeService.findById(id));
        return "trade/update";
    }

    /**
     * Update trade string.
     *
     * @param id     the id
     * @param trade  the trade
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        logger.info("update trade {}", trade);
        if (result.hasErrors()) {
            return "trade/update";
        }
        List<Trade> trades = tradeService.saveOrUpdate(trade);
        model.addAttribute("trades", trades);
        return "redirect:/trade/list";
    }

    /**
     * Delete trade string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("delete trade by Id {}", id);
        List<Trade> trades = tradeService.delete(id);
        model.addAttribute("trades", trades);
        return "redirect:/trade/list";
    }
}
