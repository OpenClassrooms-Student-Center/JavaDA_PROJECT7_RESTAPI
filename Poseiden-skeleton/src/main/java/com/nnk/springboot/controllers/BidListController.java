package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class BidListController {
    // TODO: Inject Bid service
    @Autowired
    private BidService bidService;

    @RequestMapping(value = "/bidList/list",method = RequestMethod.GET)
    public String home(Model model)
    {
        List<BidList> bidList=bidService.findAll();
        model.addAttribute("bidList",bidList);
        // TODO: call service find all bids to show to the view
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        BidList bidList = new BidList();
        model.addAttribute("bidList",bidList);

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            bidService.save(bidList);
            model.addAttribute("bidList", bidService.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
        // TODO: check data valid and save to db, after saving return bid list
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
