package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.RuleNameService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for ruleName.
 */
@Controller
@RequestMapping("/ruleName")
@RequiredArgsConstructor
public class RuleNameController {

    /**
     * Rule name service.
     */
    private final RuleNameService ruleNameService;

    /**
     * Home page.
     * @param model Model
     * @param user User
     * @return View name
     */
    @GetMapping("/list")
    public String home(Model model, @AuthenticationPrincipal User user) {
        // Find all RuleName, add to model
        model.addAttribute("remoteUser", user.getUsername());
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Show add form.
     * @param bid RuleName
     * @return View name
     */
    @GetMapping("/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * Validate ruleName and save to db.
     * @param ruleName RuleName
     * @param result BindingResult
     * @param model Model
     * @return View name
     */
    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleNameService.save(ruleName);
        }
        return "ruleName/add";
    }

    /**
     * Show update form.
     * @param id Integer
     * @param model Model
     * @return View name
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("ruleName", ruleNameService.findById(id));
        } catch (RequestedObjectNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
        return "ruleName/update";
    }

    /**
     * Update ruleName and return ruleName list.
     * @param id Integer
     * @param ruleName RuleName
     * @param result BindingResult
     * @param model Model
     * @return View name
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleName.setRulenameid(id);
            ruleNameService.save(ruleName);
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Delete ruleName and return ruleName list.
     * @param id Integer
     * @param model Model
     * @return View name
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}

