package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class BidListController {
    private final BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * This method displays the list of all bidLists in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * This method displays a form to add a new bidList to the database<br>
     * The bidList parameter will be overwritten with the form's values
     * @param bidList an empty bidList object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/bidList/add")
    public String addBidListForm(BidList bidList) {
        return "bidList/add";
    }

    /**
     * This method adds a bidList to the database, after checking its validity<br>
     * Upon resolution it redirects to bidList/list
     * @param bidList a bidList object containing the bidList to be added to the database
     * @param result the result of the bidList's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.save(bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * This method displays information about a specific bidList<br>
     * The displayed information can be modified
     * @param id the id of the bidList to display
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid bidList Id:" + id)));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * This method updates a bidList in the database, after encrypting its password<br>
     * Upon resolution it redirects to bidList/list
     * @param id the id of the bidList to update
     * @param bidList the new bidList attributes
     * @param result the result of the new bidList validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBidList(@PathVariable("id") Integer id, @Valid BidList bidList,
                              BindingResult result, Model model) {
        if(!result.hasErrors()) {
            bidListService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            bidListService.save(bidList);
            return "redirect:/bidList/list";
        }
        return "redirect:/bidList/update/{id}";
    }

    /**
     * This method deletes a bidList from the database<br>
     * Upon resolution it redirects to bidList/list
     * @param id the id of the bidList to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBidList(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
