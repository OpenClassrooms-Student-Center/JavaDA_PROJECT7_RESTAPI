package com.poseidon.api.controllers;

import com.poseidon.api.model.Bid;
import com.poseidon.api.model.dto.BidDto;
import com.poseidon.api.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class BidController {
    @Autowired
    BidService bidService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bids", bidService.findAllBids());

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidDto bidDto) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidDto bidDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {

            Bid newBid = bidService.convertDtoToEntity(bidDto);
            bidService.createBid(newBid);

            redirectAttributes.addFlashAttribute("message",
                    String.format("Bid with id " + newBid.getId() + " was successfully created"));

            model.addAttribute("bids", bidService.findAllBids());

            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Bid bidToUpdate = bidService.findBidById(id);
        BidDto bidDto = bidService.convertEntityToDto(bidToUpdate);
        model.addAttribute("bidDto", bidDto);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Long id, @Valid BidDto bidDto,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "bidList/update";
        }

        Bid updatedBid = bidService.convertDtoToEntity(bidDto);
        bidService.updateBid(id, updatedBid);
        redirectAttributes.addFlashAttribute("message", String.format("Bid with id " + id + " was successfully updated"));
        model.addAttribute("bids", bidService.findAllBids());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        bidService.deleteBid(id);
        redirectAttributes.addFlashAttribute("message", String.format("Bid with id " + id + " was successfully deleted"));
        model.addAttribute("bids", bidService.findAllBids());

        return "redirect:/bidList/list";
    }
}
