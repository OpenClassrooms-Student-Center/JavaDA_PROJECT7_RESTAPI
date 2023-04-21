package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BidListController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired
    private BidListService bidListService;
    //@Autowired
    //BidListRepository bidListRepository;
    public BidListController(BidListService bidListService){
        this.bidListService = bidListService;
        //this.bidListRepository=bidListRepository;
    }
    // TODO: Inject Bid service


//DISPLAY 'LIST OF BIDLISTS' PAGE
    @RequestMapping("/bidList/list")
    public String homeDisplayBidListsPage(Model model) {
            log.info("REQUEST /bidList/list");
            model.addAttribute("listOfBidList", bidListService.findAll());
            log.info("attribute listOfBidList added to Model");
            // TODO: call service find all bids to show to the view
            return "bidList/list";
    }
//DISPLAY 'ADD BIDLIST' FORM
    @GetMapping("/bidList/add")
    public String displayAddBidForm(BidList bid) {
        log.info("GET form /bidList/add");
        return "bidList/add";
    }
// CREATE NEW BIDLIST
    @PostMapping("/bidList/validate")
    public String validateBidList(@Valid BidList bidList, BindingResult result, Model model) {
        log.info("POST /bidList/validate");
        if(result.hasErrors()){
            log.error("BidList to create has errors");
            return ("bidList/add");
        }
        try {
            log.info("no errors in bidlist");
            bidListService.validateNewBidList(bidList);
            log.info("bidlist validated with account " + bidList.getAccount());
        } catch (Exception e ) {
            // TODO: check data valid and save to db, after saving return bid list
            log.error("BidList could not be created");
            return "bidList/add";
        }
        return "redirect:/bidList/list";
    }
//DISPLAY 'UPDATE BIDLIST' FORM
    @GetMapping("/bidList/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /bidList/update/{id} with id " + id);
            // TODO: get Bid by Id and to model then show to the form
            BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            log.info("attribute added to Model : bidList with id "+ bidList.getBid_list_id());
            return "bidList/update";
        }catch(Exception e){
            log.error("bidList update form with id "+id+" could not be displayed");
            return "bidList/list";
        }
    }
//UPDATE BIDLIST
    @PostMapping("/bidList/update/{id}")// PUT MAPPING?
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList updatedBidListEntity,
                             BindingResult result, Model model) {
        log.info("POST /bidList/update/{id} with id " + id);
        try {
            if (result.hasErrors()) {
                log.error("bildlist to update has errors");
                throw new Exception();
                //ou return "redirect:/bidList/list??
            }
            BidList updatedAndSavedBidList = bidListService.updateBidList(id, updatedBidListEntity);
            model.addAttribute("listOfBidList", bidListService.findAll());
            log.info("attribute listOfBidList added to model");

        }catch(Exception e){
            log.error("bidList with id "+ id+ " could not be updated");
            return "redirect:/bidList/update/"+id+"";
        }
        return "redirect:/bidList/list";
    }
//DELETE BIDLIST
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /bidList/delete/{id} with id " + id);
            // TODO: Find Bid by Id and delete the bid, return to Bid list
            bidListService.deleteBidList(id);
            log.info("bidList with id " + id + "deleted");
            return "redirect:/bidList/list";
        }catch(Exception e){
            log.error("bidList with id "+ id+ " could not be deleted");
            return "bidList/list";
        }
    }
}
