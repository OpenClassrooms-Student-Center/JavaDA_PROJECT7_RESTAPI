package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    private final RuleNameService ruleNameService;
    
    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * This method displays the list of all ruleNames in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * This method displays a form to add a new ruleName to the database<br>
     * The ruleName parameter will be overwritten with the form's values
     * @param ruleName an empty ruleName object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * This method adds a ruleName to the database, after checking its validity<br>
     * Upon resolution it redirects to ruleName/list
     * @param ruleName a ruleName object containing the ruleName to be added to the database
     * @param result the result of the ruleName's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleNameService.save(ruleName);
            return "redirect:/ruleName/list";
        }            
        return "ruleName/add";
    }

    /**
     * This method displays information about a specific ruleName<br>
     * The displayed information can be modified
     * @param id the id of the ruleName to display
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * This method updates a ruleName in the database, after encrypting its password<br>
     * Upon resolution it redirects to ruleName/list
     * @param id the id of the ruleName to update
     * @param ruleName the new ruleName attributes
     * @param result the result of the new ruleName validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleNameService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            ruleNameService.save(ruleName);
            return "redirect:/ruleName/list";
        }
        return "redirect:/ruleName/update/{id}";
    }

    /**
     * This method deletes a ruleName from the database<br>
     * Upon resolution it redirects to ruleName/list
     * @param id the id of the ruleName to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
