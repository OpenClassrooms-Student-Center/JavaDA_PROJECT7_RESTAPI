package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import Exceptions.AlreadyExistException;

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.findAll());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(Model model) {
		model.addAttribute("bidList", new BidList());
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid @ModelAttribute("bidList") BidList bid, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/add";
		} else {
			try {
				bidListService.createBidList(bid);
			} catch (AlreadyExistException e) {
				model.addAttribute("message", e.getMessage());
			}
			return "redirect:/bidList/list";
		}
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findById(id);
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/update";
		} else {
			try {
				bidListService.updateBidList(bidList, id);
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
			}
			return "redirect:/bidList/list";
		}
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		bidListService.deleteById(id);
		return "redirect:/bidList/list";
	}
}
