package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class BidListController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired
    private BidListService bidListService;
    //@Autowired
    BidListRepository bidListRepository;
    public BidListController(BidListService bidListService, BidListRepository bidListRepository){
        this.bidListService = bidListService;
        this.bidListRepository=bidListRepository;

    }
    // TODO: Inject Bid service
//DISPLAY LIST OF BIDLISTS PAGE
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("listOfBidList", bidListService.findAll());
    // TODO: call service find all bids to show to the view
        return "bidList/list";
    }
//DISPLAY ADD BIDLIST FORM
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    // CREATE NEW BIDLIST
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.validateNewBidList(bidList);
            return "redirect:/bidList/list";
        }
        // TODO: check data valid and save to db, after saving return bid list

        return "bidList/add";
    }
//DISPLAY UPDATE BIDLIST FORM
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.getBidListById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }
//UPDATE BIDLIST
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList updatedBidListEntity,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/list";
        }
        BidList updatedAndSavedBidList = bidListService.updateBidList(id, updatedBidListEntity );

        model.addAttribute("listOfBidList", bidListService.findAll());
        return "redirect:/bidList/list";
    }
//DELETE BIDLIST
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
