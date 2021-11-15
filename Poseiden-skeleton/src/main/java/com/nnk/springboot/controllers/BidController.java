package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.services.CurvePointService;
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


@Controller
@AllArgsConstructor
public class BidController {
    private static Logger logger = LoggerFactory.getLogger(BidController.class);
    private BidService bidService;

    @RequestMapping("/bid/list")
    public String home(Model model)
    {
        logger.info("get list");
        model.addAttribute("bids", bidService.findAll());
        return "bid/list";
    }

    @GetMapping("/bid/add")
    public String addBidForm(Bid bid) {
        return "bid/add";
    }

    @PostMapping("/bid/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model) {
        logger.info("validate bid");
        if (!result.hasErrors()) {
            logger.info("add bid {}", bid);
            List<Bid> bids = bidService.saveOrUpdate(bid);
            logger.info("saved curvePoint {}", bid);
            model.addAttribute("bids", bids);
            return "redirect:/bid/list";
        }

        logger.error("bid validate error");
        return "curvepoint/add";
    }

    @GetMapping("/bid/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find bid by id {}", id);
        model.addAttribute("bid", bidService.findById(id));
        return "bid/update";
    }

    @PostMapping("/bid/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model) {
        logger.info("update bid {}", bid);
        if (result.hasErrors()) {
            return "bid/update";
        }
        List<Bid> bids = bidService.saveOrUpdate(bid);
        model.addAttribute("bids", bids);
        return "redirect:/bid/list";
    }

    @GetMapping("/bid/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("delete bid by Id {}", id);
        List<Bid> bids = bidService.delete(id);
        model.addAttribute("bids", bids);
        return "redirect:/bid/list";
    }
}
