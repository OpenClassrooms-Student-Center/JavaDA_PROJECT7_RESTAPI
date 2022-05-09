package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.findAll());
		LOGGER.info("BidLists List");
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(Model model) {
		model.addAttribute("bidList", new BidList());
		LOGGER.info("BidList add form");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid @ModelAttribute("bidList") BidList bid, BindingResult result, Model model) {
		if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "bidList/add";
		} else {
			try {
				bidListService.createBidList(bid);
				LOGGER.info("Bidlist added");
			} catch (AlreadyExistException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This bidlist already exists");
			}
			return "redirect:/bidList/list";
		}
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findById(id);
		LOGGER.info("Bidlist to update found");
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "bidList/update";
		} else {
			try {
				bidListService.updateBidList(bidList, id);
				LOGGER.info("Bidlist updated");
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This bidlist doesn't exist");
			}
			return "redirect:/bidList/list";
		}
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		LOGGER.info("Deleting Bidlist with id:" + id);
		bidListService.deleteById(id);
		return "redirect:/bidList/list";
	}
}
