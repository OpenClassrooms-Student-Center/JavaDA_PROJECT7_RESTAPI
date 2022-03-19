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

import javax.validation.Valid;

@Controller
public class RuleNameController {
	@Autowired
	private RuleNameService ruleNameService;

	/**
	 * displays RuleName list retrieved from database
	 */
	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
		return "ruleName/list";
	}

	/**
	 * displays RuleName to add
	 */
	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		return "ruleName/add";
	}

	/**
	 * validates new RuleName
	 * 
	 * @param ruleName: RuleName to create and save
	 * @param result:   form result to validate
	 * @return RuleName list displayed if validated, new add form with errors
	 *         displayed else
	 */
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			ruleNameService.saveRuleName(ruleName);
			model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
			return "redirect:/ruleName/list";
		}
		return "ruleName/add";
	}

	/**
	 * displays RuleName form to update
	 * 
	 * @param id : id of the RuleName to update
	 * @return return RuleName form to update
	 */
	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameService.findRuleNameById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	/**
	 * validates updated RuleName
	 * 
	 * @param id      : id of the RuleName to update
	 * @param rulName : RuleName to validate
	 * @param result  : form result to validate
	 * @return RuleName list with this RuleName updated, RuleName to update form
	 *         with errors displayed else
	 */
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/ruleName/update";
		}
		ruleName.setId(id);
		ruleNameService.saveRuleName(ruleName);
		model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
		return "redirect:/ruleName/list";
	}

	/**
	 * deletes selected RuleName from database
	 * 
	 * @param id: id of the RuleName to delete
	 * @return the RuleName list after selected RuleName deleted
	 */
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameService.findRuleNameById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		ruleNameService.deleteRuleName(ruleName);
		model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
		return "redirect:/ruleName/list";
	}
}
