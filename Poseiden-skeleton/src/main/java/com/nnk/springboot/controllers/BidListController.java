package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

@Controller
public class BidListController {
    private IBidListService bidListService;

    public BidListController(IBidListService bidListService) {
	this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {

	List<BidList> bidListResult = new ArrayList<BidList>();
	this.bidListService.getBidLists().forEach(bidListResult::add);
	model.addAttribute("bidList", bidListResult);

	return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
	return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "bidList/add";
	}

	this.bidListService.saveBidList(bid);

	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	BidList bidListResult = this.bidListService.getBidListById(id).get();

	model.addAttribute("bidList", bidListResult);

	return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "bidList/update";
	}

	// Save also updates automatically with Entity Framework
	bidList.setBidListId(id);
	this.bidListService.saveBidList(bidList);

	// Retrieve the BidList with updated values
	List<BidList> bidListResult = new ArrayList<BidList>();
	this.bidListService.getBidLists().forEach(bidListResult::add);
	model.addAttribute("bidList", bidListResult);

	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
	this.bidListService.deleteBidListById(id);

	// Retrieve the BidList with updated values
	List<BidList> bidListResult = new ArrayList<BidList>();
	this.bidListService.getBidLists().forEach(bidListResult::add);
	model.addAttribute("bidList", bidListResult);

	return "redirect:/bidList/list";
    }
}
