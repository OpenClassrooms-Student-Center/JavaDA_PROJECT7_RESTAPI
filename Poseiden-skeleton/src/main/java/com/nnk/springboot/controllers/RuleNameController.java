package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RuleNameController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private RuleNameService ruleNameService;
    public RuleNameController(RuleNameService ruleNameService){
        this.ruleNameService = ruleNameService;
    }
    // TODO: Inject RuleName service

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("listOfRulenames", ruleNameService.findAll());
        // TODO: find all RuleName, add to model
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }


//ADD NEW RULENAME
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (!result.hasErrors()) {
            ruleNameService.validateNewRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }
//DISPLAY UPDATE RULENAME FORM
    @GetMapping("/rulename/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }
//UPDATE RULENAME
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName updatedRuleNameEntity,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            return "ruleName/list";
        }
        RuleName updatedAndSavedRuleName = ruleNameService.updateRuleName(id, updatedRuleNameEntity );

        model.addAttribute("listOfRulenames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/rulename/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
}
