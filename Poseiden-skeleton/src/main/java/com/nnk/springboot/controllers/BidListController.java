package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.FormatDate;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BidListController {

    private static final String REDIRECTBIDLIST = "redirect:/bidList/list";
    private static final String BIDS = "bids";

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private FormatDate formatDateFormat;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute(BIDS, bidListRepository.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, HttpServletResponse response,
            @RequestParam String bidListDateString, @RequestParam String creationDateString,
            @RequestParam String revisionDateString) throws ParseException {

        setDateToFormat(bid, bidListDateString, creationDateString, revisionDateString);

        if (!result.hasErrors()) {
            bidListRepository.save(bid);
            model.addAttribute(BIDS, bidListRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201
            return REDIRECTBIDLIST;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model, HttpServletResponse response,
            @RequestParam String bidListDateString, @RequestParam String creationDateString,
            @RequestParam String revisionDateString) throws ParseException {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400
            return "bidList/update";
        }

        setDateToFormat(bidList, bidListDateString, creationDateString, revisionDateString);

        bidList.setBidlistId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        response.setStatus(HttpServletResponse.SC_CREATED); // response 201
        return REDIRECTBIDLIST;
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute(BIDS, bidListRepository.findAll());
        return REDIRECTBIDLIST;
    }

    private void setDateToFormat(@Valid BidList bid, String bidListDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        if (!bidListDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(bidListDateString);
            bid.setBidListDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!creationDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(creationDateString);
            bid.setCreationDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!revisionDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(revisionDateString);
            bid.setRevisionDate(formatDateFormat.getTimestampFromatDate());
        }

    }

}
