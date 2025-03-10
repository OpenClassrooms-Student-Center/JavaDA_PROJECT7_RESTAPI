package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller to manage RuleName entities.
 */
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private final RuleNameService ruleNameService;

    /**
     * Constructor-based dependency injection.
     *
     * @param ruleNameService the rule name service
     */
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Displays the list of rule names.
     *
     * @param model the model
     * @return the rule name list view
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername() );
        return "ruleName/list";
    }

    /**
     * Displays the form to add a new rule name.
     *
     * @return the add rule name view
     */
    @GetMapping("/add")
    public String addRuleNameForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * Validates and saves a new rule name.
     *
     * @param ruleName the rule name object
     * @param result   validation result
     * @return the redirect path
     */
    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "ruleName/add";
        }
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form to update an existing rule name.
     *
     * @param id    the rule name id
     * @param model the model
     * @return the update rule name view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id).orElseThrow(()-> new IllegalArgumentException("Invalid RuleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Updates an existing rule name.
     *
     * @param id       the rule name id
     * @param ruleName the updated rule name object
     * @param result   validation result
     * @return the redirect path
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a rule name.
     *
     * @param id the rule name id
     * @return the redirect path
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id).orElseThrow(()-> new IllegalArgumentException("Invalid RuleName Id:" + id));
        ruleNameService.deleteRuleNameById(id);
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
