package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RuleNameController {
    // TODO DONE: Inject RuleName services
    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        // TODO DONE: find all RuleName, add to model
        List<RuleName> ruleName = ruleNameService.findAllRuleName();
        model.addAttribute("ruleNames",ruleName);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO DONE: check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            ruleNameService.saveRuleName(ruleName);
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            return "ruleName/add";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: get RuleName by Id and to model then show to the form
        if (!ruleNameService.checkIfIdExists(id)) {
            return "redirect:/ruleName/list";
        }
        RuleName ruleName = ruleNameService.findByRuleNameId(id);
        model.addAttribute("ruleName",ruleName);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO DONE: check required fields, if valid call services to update RuleName and return RuleName list
        if (!ruleNameService.checkIfIdExists(id)) {
            return "redirect:/ruleName/list";
        }
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            ruleNameService.saveRuleName(ruleName);
            return "redirect:/ruleName/list";
        }catch (Exception e) {
            return "ruleName/add";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: Find RuleName by Id and delete the RuleName, return to Rule list
        if (!ruleNameService.checkIfIdExists(id)) {
            return "redirect:/ruleName/list";
        }
        RuleName ruleName = ruleNameService.findByRuleNameId(id);
        ruleNameService.deleteRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAllRuleName());

        return "redirect:/ruleName/list";
    }
}
