package com.poseidon.api.controllers;

import com.poseidon.api.model.Rule;
import com.poseidon.api.model.dto.RuleDto;
import com.poseidon.api.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RuleController {

    @Autowired
    RuleService ruleService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("rules", ruleService.findAllRules());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleDto ruleDto) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleDto ruleDto, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {

            Rule newRule = ruleService.convertDtoToEntity(ruleDto);
            ruleService.createRule(newRule);

            redirectAttributes.addFlashAttribute("message",
                    String.format("Rule with id '%d' was successfully created", newRule.getId()));

            model.addAttribute("rules", ruleService.findAllRules());

            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Rule ruleNameToUpdate = ruleService.findRuleById(id);
            RuleDto ruleDto = ruleService.convertEntityToDto(ruleNameToUpdate);
            ruleDto.setId(id);
            model.addAttribute("ruleDto", ruleDto);
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("message", error.getMessage());
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Long id, @Valid RuleDto ruleDto, BindingResult result,
                                 Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "ruleName/update";
        }

        Rule updatedRuleName = ruleService.convertDtoToEntity(ruleDto);
        ruleService.updateRule(id, updatedRuleName);

        redirectAttributes.addFlashAttribute("message",
                String.format("Rule with id '%d' was successfully updated", id));

        model.addAttribute("rules", ruleService.findAllRules());

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ruleService.deleteRule(id);
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("message", error.getMessage());
            return "redirect:/ruleName/list";
        }

        redirectAttributes.addFlashAttribute("message",
                String.format("Rule with id '%d' was successfully deleted", id));

        model.addAttribute("rules", ruleService.findAllRules());

        return "redirect:/ruleName/list";
    }
}