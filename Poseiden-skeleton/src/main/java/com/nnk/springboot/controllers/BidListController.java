package com.nnk.springboot.controllers;


import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddBidListForm;
import com.nnk.springboot.interfaces.BidListService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Controller
public class BidListController {

    private final BidListRepository bidListRepository;
    private final BidListService bidListService;


    @RequestMapping("/bidList/list")
    public String home(Model model) {
        log.info("home: show bidList/list");
        model.addAttribute("bidLists", bidListRepository.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm() {
        log.info("addBidForm: show bidList/add"  );
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid AddBidListForm addBidListForm, RedirectAttributes redirectAttributes) {
        log.info("validate: account; " + addBidListForm.getAccount() + " type; " + addBidListForm.getType() + " quantity; " + addBidListForm.getBidQuantity());

        try {
            bidListService.validate(addBidListForm);
        } catch (NumberFormatException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/bidList/add";
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("showUpdateForm: " + id);
        model.addAttribute("bidList", bidListRepository.findBidListByBidListId(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        log.info("updateBid: " + id);
        if(result.hasErrors()){
          redirectAttributes.addAttribute("error", true);
        }
        try {
            bidListService.updateBid(id, bidList);
        } catch (NumberFormatException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/bidList/update/" + id;
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        log.info("deleteBid:" + id);
        bidListService.deleteBid(id);
        return "redirect:/bidList/list";
    }
}
