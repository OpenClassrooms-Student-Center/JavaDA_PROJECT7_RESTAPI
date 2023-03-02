package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Bid list controller.
 */
@Controller
@Slf4j
public class BidListController {
    private final BidListService bidListService;

    /**
     * Instantiates a new Bid list controller.
     *
     * @param bidListService the bid list service
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Home page to list all items.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidList", bidListService.findAll());
        log.info("Displayed list of bid items");
        return "bidList/list";
    }

    /**
     * Add bid form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bid", new BidListDto());
        return "bidList/add";
    }

    /**
     * Validate.
     *
     * @param bid    the bid
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bid") BidListDto bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation failed for bid item");
            return "bidList/add";
        }
        bidListService.create(bid);
        log.info("Successfully created bid");
        return "redirect:/bidList/list";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bid", bidListService.findById(id));
        return "bidList/update";
    }

    /**
     * Update bid.
     *
     * @param id      the id
     * @param bidList the bid list
     * @param result  the result
     * @param model   the model
     * @return the string
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for bid item");
            return "bidList/update";
        }
        bidListService.update(id, bidList);
        log.info("Successfully updated user with ID {}", id);
        return "redirect:/bidList/list";
    }

    /**
     * Delete bid.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /bidList/delete endpoint with id {}", id);
        bidListService.delete(id);
        return "redirect:/bidList/list";
    }
}
