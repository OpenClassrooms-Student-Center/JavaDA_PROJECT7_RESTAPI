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
import com.nnk.springboot.repositories.BidListRepository;
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
    	//@todo revoir comment on ajoute un attribut sur Thymeleaf
    	model.addAllAttributes(bidListResult);
    	return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
    	return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
    	//@todo revoir bindingresult validator du P06
    	this.bidListService.saveBidList(bid);
	
    	return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
	// TODO: check required fields, if valid call service to update Bid and return
	// list Bid
    	// Update service to include "UPDATE" queries

	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
	// TODO: Find Bid by Id and delete the bid, return to Bid list
	return "redirect:/bidList/list";
    }
}
