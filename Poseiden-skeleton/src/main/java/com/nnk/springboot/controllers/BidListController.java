package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nguyen Kim Anh
 * @since 08/08/2023
 * @version 1.0
 */
@Controller
@RequestMapping("/bidList")
@RequiredArgsConstructor
public class BidListController {

    /**
     * Inject Bid service
     */
    private final BidListService bidListService;

    /**
     * Home page
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/list")
    public String home(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("remoteUser", user.getUsername());
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Add bid form
     * @param bid
     * @return
     */
    @GetMapping("/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Validate bid
     * @param bid
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.addBidList(bid);
        }
        return "bidList/add";
    }

    /**
     * Update bid form
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            BidList bidList = bidListService.findById(id);
            model.addAttribute("bidList", bidList);
        } catch (RequestedObjectNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
        return "bidList/update";
    }

    /**
     * Update bid
     * @param id
     * @param bidList
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidList.setBidlistid(id);
            bidListService.addBidList(bidList);
        }
        return "redirect:/bidList/list";
    }

    /**
     * Delete bid
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}

