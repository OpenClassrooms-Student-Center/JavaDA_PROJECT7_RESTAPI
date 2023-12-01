package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class BidListController {
    // TODO DONE: Inject Bid services
    @Autowired
    private BidListService bidListService;

    @GetMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO DONE: call services find all bids to show to the view
        List<BidList> bidList = bidListService.findAllBid();
        model.addAttribute("bidList",bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO DONE: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            return "bidList/add";
        }
        try {
            bidListService.saveBid(bid);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: get Bid by Id and to model then show to the form
        if (!bidListService.checkIfIdExists(id)) {
            return "redirect:/bidList/list";
        }
        BidList bid = bidListService.findByBidListId(id);
        model.addAttribute("bidList",bid);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO DONE: check required fields, if valid call services to update Bid and return list Bid
        if (!bidListService.checkIfIdExists(id)) {
            return "redirect:/bidList/list";
        }
        if (result.hasErrors()) {
            return "bidList/add";
        }
        try {
            bidListService.saveBid(bidList);
            return "redirect:/bidList/list";
        }catch (Exception e) {
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: Find Bid by Id and delete the bid, return to Bid list
        if (!bidListService.checkIfIdExists(id)) {
            return "redirect:/bidList/list";
        }
        BidList bid = bidListService.findByBidListId(id);
        bidListService.deleteBid(bid);
        model.addAttribute("bidList", bidListService.findAllBid());

        return "redirect:/bidList/list";
    }
}
