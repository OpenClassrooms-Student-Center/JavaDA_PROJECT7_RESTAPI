package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.services.RuleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Rule controller.
 */
@Controller
@AllArgsConstructor
public class RuleController {
    private static Logger logger = LoggerFactory.getLogger(RuleController.class);
    private final RuleService ruleService;

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/rule/list")
    public String home(Model model)
    {
        logger.info("get list");
        model.addAttribute("rules", ruleService.findAll());
        return "rule/list";
    }

    /**
     * Add rule form string.
     *
     * @param rule the rule
     * @return the string
     */
    @GetMapping("/rule/add")
    public String addRuleForm(Rule rule) {
        return "rule/add";
    }

    /**
     * Validate string.
     *
     * @param rule   the rule
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rule/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        logger.info("validate rule");
        if (!result.hasErrors()) {
            logger.info("add rule {}", rule);
            List<Rule> rules = ruleService.saveOrUpdate(rule);
            logger.info("saved rule {}", rules);
            model.addAttribute("rules", rules);
            return "redirect:/rule/list";
        }

        logger.error("rule validate error");
        return "rule/add";
    }

    /**
     * Show update form string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find rule by id {}", id);
        model.addAttribute("rule", ruleService.findById(id));
        return "rule/update";
    }

    /**
     * Update rule string.
     *
     * @param id     the id
     * @param rule   the rule
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rule/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        logger.info("update rule {}", rule);
        if (result.hasErrors()) {
            return "rule/update";
        }
        List<Rule> rules = ruleService.saveOrUpdate(rule);
        model.addAttribute("rules", rules);
        return "redirect:/rule/list";
    }

    /**
     * Delete rule string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rule/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id, Model model) {
        logger.info("delete rule by Id {}", id);
        List<Rule> rules = ruleService.delete(id);
        model.addAttribute("rules", rules);
        return "redirect:/rule/list";
    }
}
