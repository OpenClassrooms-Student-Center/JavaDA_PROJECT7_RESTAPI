package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RuleNameController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private RuleNameService ruleNameService;
    public RuleNameController(RuleNameService ruleNameService){
        this.ruleNameService = ruleNameService;
    }
    // TODO: Inject RuleName service
//DISPLAY LIST OF RULENAMES PAGE
    @RequestMapping("/ruleName/list")
    public String homeDisplayRuleNamesList(Model model) {
        log.info("REQUEST /ruleName/list");
        model.addAttribute("listOfRulenames", ruleNameService.findAll());
        // TODO: find all RuleName, add to model
        log.info("attribute listOfRuleNames added to Model");
        return "ruleName/list";
    }
//DISPLAY ADD RULENAME FORM
    @GetMapping("/ruleName/add")
    public String displayAddRuleForm(RuleName ruleName) {
        log.info("GET form /ruleName/add");
        return "ruleName/add";
    }


//CREATE NEW RULENAME
    @PostMapping("/ruleName/validate")
    public String validateRuleName(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("POST /ruleName/validate");
        // TODO: check data valid and save to db, after saving return RuleName list
        if(result.hasErrors()){
            log.error("ruleName to create has errors");
            return "ruleName/add";
        }
        try{
            ruleNameService.validateNewRuleName(ruleName);
            log.info("ruleName validated with id "+ ruleName.getRulename_id());
        }catch(Exception e){
            log.error("ruleName could not be created");
            return "ruleName/add";
        }
    return "redirect:/ruleName/list";
    }
//DISPLAY UPDATE RULENAME FORM
    @GetMapping("/rulename/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /rulename/update/{id} with id "+ id);
            RuleName ruleName = ruleNameService.getRuleNameById(id);
            model.addAttribute("ruleName", ruleName);
            log.info("attribute added to model : rulename with id "+ id );
            // TODO: get RuleName by Id and to model then show to the form
            return "ruleName/update";
        }catch(Exception e){
            log.error("ruleName update form with id "+id+" could not be displayed");
            return "ruleName/list";
        }

    }
//UPDATE RULENAME
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName updatedRuleNameEntity,
                             BindingResult result, Model model) {

        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        log.info("POST /ruleName/update/{id} with id "+id);
        if (result.hasErrors()) {
            log.error("ruleName to update has errors");
            return "ruleName/update";
        }
        try{
            RuleName updatedAndSavedRuleName = ruleNameService.updateRuleName(id, updatedRuleNameEntity );
            model.addAttribute("listOfRulenames", ruleNameService.findAll());
            log.info("attribute listOfRulenames added to model");
            return "redirect:/ruleName/list";
        }catch(Exception e){
            log.error("ruleName with id "+ id+ " could not be updated");
            return "ruleName/update";
        }

    }
//DELETE RULENAME
    @GetMapping("/rulename/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /rulename/delete/{id}");
            ruleNameService.deleteRuleName(id);
            // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
            log.info("rulename with id "+ id + "deleted");
            return "redirect:/ruleName/list";
        }catch(Exception e){
            log.error("ruleName with id "+id+ " could not be deleted");
            return "ruleName/list";
        }

    }
}
