package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.ITradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * TradeController Controller
 */
@Controller
public class TradeController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("TradeController");

    /**
     * ITradeService instance.
     */
    private ITradeService tradeService;


    /**
     * @param tradeService
     */
    public TradeController(ITradeService tradeService) {
        this.tradeService = tradeService;
    }


    /**
     * @param model
     * @return trades list
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        logger.info("@RequestMapping(\"/trade/list\")");
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * @param trade
     * @return trade add page
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        logger.info("@GetMapping(\"/trade/add\")");
        return "trade/add";
    }

    /**
     * @param trade
     * @param result
     * @param model
     * @return add new trade
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("@PostMapping(\"/trade/validate\")");
        /**form data validation*/
        if (result.hasErrors()) {
            return "/curvePoint/add";
        }
        /**save in to dataBase:*/
        tradeService.save(trade);
        return "redirect:/trade/list";
    }

    /**
     * @param id
     * @param model
     * @return update trade
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/trade/update/{id}\")");
        Optional<Trade> trade = tradeService.findById(id);
        if(trade.isPresent()){
            model.addAttribute("error", "This" + trade + "is present");
        }
        model.addAttribute("trade", trade.get());
        return "trade/update";
    }

    /**
     * @param id
     * @param trade
     * @param result
     * @param model
     * @return update trade
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        logger.info("@PostMapping(\"/trade/update/{id}\")");
        if (result.hasErrors()) {
            logger.error("result error :{}", result.getFieldError());
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.save(trade);
        model.addAttribute("trade", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * @param id
     * @param model
     * @return delete trade
     * @throws DataNotFoundException
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/trade/delete/{id}\"");
        Optional<Trade> trade = tradeService.findById(id);
        tradeService.delete(trade.get());
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
