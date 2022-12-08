package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

@Controller
public class RuleNameController {
    private IRuleNameService ruleNameService;

    public RuleNameController(IRuleNameService ruleNameService) {
	this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);

	model.addAttribute("ruleNameList", ruleNameListResult);
	return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model, RuleName ruleName) {
	return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "ruleName/add";
	}
	this.ruleNameService.saveRuleName(ruleName);

	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);

	model.addAttribute("ruleNameList", ruleNameListResult);

	return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	RuleName ruleNameResult = this.ruleNameService.getRuleNameById(id).get();

	model.addAttribute("ruleName", ruleNameResult);
	return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
	    Model model) {
	if (result.hasErrors()) {
	    return "ruleName/update/{id}";
	}
	ruleName.setRuleNameId(id);
	this.ruleNameService.saveRuleName(ruleName);

	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);

	model.addAttribute("ruleNameList", ruleNameListResult);
	return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
	this.ruleNameService.deleteRuleNameById(id);

	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);

	model.addAttribute("ruleNameList", ruleNameListResult);
	return "redirect:/ruleName/list";
    }
}
