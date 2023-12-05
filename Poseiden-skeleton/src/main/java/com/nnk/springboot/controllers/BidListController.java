package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class BidListController {
    // TODO: Inject Bid service
    private final BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidListForm(BidList bidList) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.save(bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid bidList Id:" + id)));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBidList(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if(!result.hasErrors()) {
            bidListService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            bidListService.save(bidList);
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBidList(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid bidList Id:" + id)));
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
