package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IRuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RuleNameController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("RuleNameController");

    /**
     * IRuleNameService instance.
     */
    private IRuleNameService ruleNameService;

    /**
     * @param ruleNameService
     */
    public RuleNameController(IRuleNameService ruleNameService){
        this.ruleNameService= ruleNameService;
    }


    /**
     * @param model
     * @return ruleName list page
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        logger.info("@RequestMapping(\"/ruleName/list\")");
        model.addAttribute("ruleNames",ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * @param bid
     * @return ruleName add form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        logger.info("@RequestMapping(\"/ruleName/add\")");
        return "ruleName/add";
    }

    /**
     * @param ruleName
     * @param result
     * @param model
     * @return add New RuleName /ruleName/list
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        logger.info("@PostMapping(\"/ruleName/validate\")");
        /**form data validation*/
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/ruleName/update/{id}\")");
        Optional<RuleName> ruleNames = ruleNameService.findById(id);
        if(ruleNames.isPresent()){
            model.addAttribute("error", "This " + ruleNames.get() + "is present");
        }
        model.addAttribute("ruleName", ruleNames.get());
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        logger.info("@PostMapping(\"/ruleName/update/{id}\")");
        if (result.hasErrors()) {
            logger.error("result error :{}", result.getFieldError());
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        logger.info("@GetMapping(\"/ruleName/delete/{id}\"");
        Optional<RuleName> ruleName = ruleNameService.findById(id);
        ruleNameService.delete(ruleName.get());
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
