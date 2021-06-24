package com.nnk.springboot.controllers;

import com.nnk.springboot.interfaces.RuleNameService;
import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Controller
public class RuleNameController {

    private final RuleNameRepository ruleNameRepository;
    private final RuleNameService ruleNameService;


    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        log.info("home: show ruleName/list");
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm() {
        log.info("addRuleForm: show ruleName/add"  );
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName) {
        log.info("validate: ruleName: " + ruleName.getName()
                + " Description: " + ruleName.getDescription()
                + " Json: " + ruleName.getJson()
                + "Template:" + ruleName.getTemplate()
                + "SqlStr:" + ruleName.getSqlStr()
                + "SqlPart:" + ruleName.getSqlPart());

        ruleNameService.validateRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("showUpdateForm: Rating  " + id);
        model.addAttribute("ruleName", ruleNameRepository.findRuleNameById(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("updateRuleName: " + id);
        if(result.hasErrors()){
            redirectAttributes.addAttribute("error", true);
        }
        ruleNameService.updateRuleName(id, ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        log.info("delete RuleName:" + id);
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
