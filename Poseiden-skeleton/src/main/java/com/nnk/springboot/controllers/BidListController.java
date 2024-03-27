package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class BidListController {
    private BidListService bidListService;
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request) { //  call service find all bids to show to the view
        model.addAttribute("bidLists", bidListService.findAll());
        model.addAttribute("request", request);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) { //check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.saveBidList(bid);
            return "redirect:list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) { // get Bid by Id and to model then show to the form
        model.addAttribute("bidList", bidListService.findBidListById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) { // check required fields, if valid call service to update Bid and return list Bid
        if (!result.hasErrors()) {
            bidListService.saveBidList(bidList);
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) { // Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(bidListService.findBidListById(id));
        return "redirect:/bidList/list";
    }
}
