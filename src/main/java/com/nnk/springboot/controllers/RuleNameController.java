package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.LoggerApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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

@Controller
public class RuleNameController {

    private static final String REDIRECTRULELIST = "redirect:/ruleName/list";
    private static final String RULES = "rules";

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private LoggerApi loggerApi;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(RULES, ruleNameRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, HttpServletRequest request,
            HttpServletResponse response) {

        if (!result.hasErrors()) {
            ruleNameRepository.save(ruleName);
            model.addAttribute(RULES, ruleNameRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTRULELIST;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("ruleName", ruleName);

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "ruleName/update";
        }

        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("users", ruleNameRepository.findAll());
        response.setStatus(HttpServletResponse.SC_CREATED); // response 201

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTRULELIST;
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute(RULES, ruleNameRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfo(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTRULELIST;
    }
}
