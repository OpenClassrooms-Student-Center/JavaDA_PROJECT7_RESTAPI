package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;


import com.nnk.springboot.services.BidListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	/**
	 * displays BidList list retrieved from database
	 */
	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.findAllBidLists());
		return "bidList/list";
	}

	/**
	 * displays form for new BidList to add
	 */
	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	/**
	 * validates new BidList
	 * 
	 * @param bid:    BidList to create and save
	 * @param result: form result to validate
	 * @return BidList list displayed if validated, new add form with errors
	 *         displayed else
	 */
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			bidListService.saveBidList(bid);
			model.addAttribute("bidLists", bidListService.findAllBidLists());
			return "redirect:/bidList/list";
		}
		return "/bidList/add";
	}

	/**
	 * displays BidList form to update
	 * 
	 * @param id : id of the BidList to update
	 * @return return BidList form to update
	 */
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	/**
	 * validates updated BidList
	 * 
	 * @param id      : id of the BidList to update
	 * @param bidList : BidList to validate
	 * @param result  : form result to validate
	 * @return BidList list with this BidList updated, BidList to update form
	 *         with errors displayed else
	 */
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
			BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/bidList/update";
		}
		bidList.setBidListId(id);
		bidListService.saveBidList(bidList);
		model.addAttribute("bidLists", bidListService.findAllBidLists());
		return "redirect:/bidList/list";
	}

	/**
	 * deletes selected BidList from database
	 * 
	 * @param id: id of the BidList to delete
	 * @return the BidList list after selected BidList deleted
	 */
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		bidListService.deleteBidList(bidList);
		model.addAttribute("bidLists", bidListService.findAllBidLists());
		return "redirect:/bidList/list";
	}
}
