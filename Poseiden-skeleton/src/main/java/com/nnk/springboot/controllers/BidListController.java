package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IBidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger("BidListController");


    private IBidListService bidListService;

    public BidListController(IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) {
        // TODO: call service find all bids to show to the view
        logger.info("@RequestMapping(\"/bidList/list\")");
        model.addAttribute("bidList", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("@GetMapping(\"/bidList/add\")");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.info("@PostMapping(\"/bidList/validate\")");
        /**form data validation*/
        if (result.hasErrors()) {
            return "/bidList/add";
        }
        /**save in to dataBase:*/
        bidListService.save(bid);
        //redirection do not use the current Model
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/bidList/update/{id}\")");
        Optional<BidList> bidList = bidListService.findById(id);
       if(bidList.isPresent()){
           model.addAttribute("Error", "This "+ bidList + " is present");
       }

        model.addAttribute("bidList", bidList.get());
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, BidList bidList,
                            BindingResult result, Model model) throws DataNotFoundException {
        logger.info("@PostMapping(\"/bidList/update/{id}\")");
        if (result.hasErrors()) {
            logger.error("result error :{}", result.getFieldError());
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.save(bidList);
        model.addAttribute("bidList", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/bidList/delete/{id}\"");
        Optional<BidList> bidList = bidListService.findById(id);
        bidListService.delete(bidList.get());
        model.addAttribute("bids", bidListService.findAll());
        return "redirect:/bidList/list";
    }


    //nouvelle classe ou ici on fai les controllers pour postman => API
}
