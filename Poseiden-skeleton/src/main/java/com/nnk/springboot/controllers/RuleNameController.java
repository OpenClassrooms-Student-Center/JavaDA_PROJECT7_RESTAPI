package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.service.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type RuleName controller.
 */
@Controller
@Slf4j
public class RuleNameController {
    private final RuleNameService ruleNameService;

    /**
     * Instantiates a new RuleName controller.
     *
     * @param ruleNameService the rule name service
     */
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Home page to list all items.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNameList", ruleNameService.findAll());
        log.info("Displayed list of ruleName items");
        return "ruleName/list";
    }

    /**
     * Add rule form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleNameDto());
        return "/ruleName/add";
    }

    /**
     * Validate.
     *
     * @param ruleName the rule name
     * @param result   the result
     * @param model    the model
     * @return the string
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameDto ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation failed for ruleName item");
            return "ruleName/add";
        }
        ruleNameService.create(ruleName);
        log.info("Successfully created ruleName");
        return "redirect:/ruleName/list";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /bidList/delete endpoint with id {}", id);
        model.addAttribute("ruleName", ruleNameService.findById(id));
        return "ruleName/update";
    }

    /**
     * Update rule name.
     *
     * @param id       the id
     * @param ruleName the rule name
     * @param result   the result
     * @param model    the model
     * @return the string
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for ruleName item");
            return "ruleName/update";
        }
        ruleNameService.update(id, ruleName);
        log.info("Successfully updated ruleName with ID {}", id);
        return "redirect:/ruleName/list";
    }

    /**
     * Delete rule name.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /ruleName/delete endpoint with id {}", id);
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}
