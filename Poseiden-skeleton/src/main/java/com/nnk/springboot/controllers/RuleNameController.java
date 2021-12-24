package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@Controller
public class RuleNameController {
    
	@Autowired
	private RuleNameService ruleNameService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
       model.addAttribute("ruleNameList", ruleNameService.findAll());
		LOGGER.info("Rule Name List");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
    	model.addAttribute("ruleName", new RuleName());
    	LOGGER.info("RuleName add form");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "ruleName/add";
		} else {
		ruleNameService.createRuleName(ruleName);
		LOGGER.info("RuleName added");
        return "redirect:/ruleName/list";
		}
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id);
		LOGGER.info("RuleName to update found");
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "ruleName/update";
		} else {
			try {
				ruleNameService.updateRuleName(ruleName, id);
				LOGGER.info("RuleName updated");
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This RuleName doesn't exist");
			}
			return "redirect:/ruleName/list";
		}
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		LOGGER.info("Deleting RuleName with id:" + id);
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
