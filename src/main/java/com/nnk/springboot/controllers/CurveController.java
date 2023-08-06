package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.FormatDate;
import com.nnk.springboot.service.LoggerApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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
    private FormatDate formatDateFormat;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {

        String remoteUserString = request.getRemoteUser();
        model.addAttribute("remoteUserString", remoteUserString);
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

        setDateToFormat(curvePoint, asOfDateString, creationDateString);

        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute(CURVES, curvePointRepository.findAll());
            response.setStatus(HttpServletResponse.SC_CREATED); // response 201

            String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
            LOGGER.info(messageLoggerInfo);

            return REDIRECTCURVEPOINTLIST;
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

        setDateToFormat(curvePoint, asOfDateString, creationDateString);

        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("users", curvePointRepository.findAll());
        response.setStatus(HttpServletResponse.SC_CREATED); // response 201

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return REDIRECTCURVEPOINTLIST;
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

    private void setDateToFormat(@Valid CurvePoint curvePoint, String asOfDateString, String creationDateString)
            throws ParseException {

        if (!asOfDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(asOfDateString);
            curvePoint.setAsOfDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!creationDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(creationDateString);
            curvePoint.setCreationDate(formatDateFormat.getTimestampFromatDate());
        }
    }

}
