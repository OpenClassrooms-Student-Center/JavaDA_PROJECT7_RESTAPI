package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IRuleNameService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class RuleNameController {
	private IRuleNameService ruleNameService;

    public RuleNameController(IRuleNameService ruleNameService) {
    	this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
    	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
    	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);
    	
    	model.addAttribute("ruleNameList", ruleNameListResult);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model, RuleName ruleName) {
//    	model.addAttribute("ruleNameForm", new RuleName());

    	return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
//    	bidValidator.validate(ruleName, result);
//    	if (result.hasErrors()) {
//    	    return "ruleName/add";
//    	}
    	this.ruleNameService.saveRuleName(ruleName);

    	List<RuleName> ruleNameListResult = new ArrayList<RuleName>();
    	this.ruleNameService.getRuleNames().forEach(ruleNameListResult::add);
    	
    	model.addAttribute("ruleNameList", ruleNameListResult);    	

    	return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	RuleName ruleNameResult = this.ruleNameService.getRuleNameById(id).get();
    	
    	model.addAttribute("ruleNameForm", ruleNameResult);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
//    	bidValidator.validate(ruleName, result);
//    	if (result.hasErrors()) {
//    	    return "ruleName/update/{id}";
//    	}
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
