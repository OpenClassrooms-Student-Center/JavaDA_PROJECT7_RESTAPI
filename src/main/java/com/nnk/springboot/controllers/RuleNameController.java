package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.ValidInput;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class RuleNameController {

    private static final String REDIRECTRULELIST = "redirect:/ruleName/list";
    private static final String RULES = "rules";

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private ValidInput validInput;

    @Autowired
    private LoggerApi loggerApi;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("httpServletRequest", request);
        model.addAttribute(RULES, ruleNameRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, HttpServletRequest request,
            HttpServletResponse response) {

        if (!result.hasErrors()) {

            validInput.addRule(ruleName);

            if (validInput.getAddRule()) {
                model.addAttribute(RULES, ruleNameRepository.findAll());
                response.setStatus(HttpServletResponse.SC_CREATED); // response 201

                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
                LOGGER.info(messageLoggerInfo);

                return REDIRECTRULELIST;
            } else {
                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Rule not added!");
                LOGGER.info(messageLoggerInfo);

                return "ruleName/add";
            }
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("ruleName", ruleName);

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "ruleName/update";
        }

        validInput.updateRule(ruleName, id);

        if (validInput.getUpdateRule()) {
            model.addAttribute("users", ruleNameRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTRULELIST;
        } else {
            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Rule not updated!");
            LOGGER.info(messageLoggerInfo);

            return "ruleName/update";
        }

    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute(RULES, ruleNameRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTRULELIST;
    }
}
