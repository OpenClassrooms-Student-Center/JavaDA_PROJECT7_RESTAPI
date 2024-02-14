package com.nnk.springboot.controllers;

import java.text.ParseException;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.ValidInput;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CurveController {

    private static final String REDIRECTCURVEPOINTLIST = "redirect:/curvePoint/list";
    private static final String CURVES = "curves";

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(CurveController.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Autowired
    private ValidInput validInput;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("httpServletRequest", request);
        model.addAttribute(CURVES, curvePointRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid, HttpServletRequest request, HttpServletResponse response) {

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model,
            HttpServletResponse response, @RequestParam String asOfDateString, @RequestParam String creationDateString,
            HttpServletRequest request)
            throws ParseException {

        if (!result.hasErrors()) {

            validInput.addCurvePoint(curvePoint, asOfDateString, creationDateString);

            if (validInput.getAddCurvePoint()) {
                model.addAttribute(CURVES, curvePointRepository.findAll());
                response.setStatus(HttpServletResponse.SC_CREATED); // response 201

                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
                LOGGER.info(messageLoggerInfo);

                return REDIRECTCURVEPOINTLIST;
            } else {
                String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Curve Point not added!");
                LOGGER.info(messageLoggerInfo);
                return "curvePoint/add";
            }
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("curvePoint", curvePoint);

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
            BindingResult result, Model model, HttpServletResponse response, @RequestParam String asOfDateString,
            @RequestParam String creationDateString, HttpServletRequest request) throws ParseException {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // response 400

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return "curvePoint/update";
        }

        validInput.updateCurvePoint(curvePoint, id, asOfDateString, creationDateString);

        if (validInput.getUpdateCurvePoint()) {
            model.addAttribute("users", curvePointRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTCURVEPOINTLIST;
        } else {
            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "Curve Point not updated!");
            LOGGER.info(messageLoggerInfo);
            return "curvePoint/update";
        }

    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute(CURVES, curvePointRepository.findAll());

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTCURVEPOINTLIST;
    }

}
