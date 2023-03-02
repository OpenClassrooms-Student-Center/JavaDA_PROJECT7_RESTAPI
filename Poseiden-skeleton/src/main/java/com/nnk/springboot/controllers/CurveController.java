package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Curve controller.
 */
@Controller
@Slf4j
public class CurveController {
    private final CurvePointService curvePointService;

    /**
     * Instantiates a new Curve controller.
     *
     * @param curvePointService the curve point service
     */
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Home page to list all items.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curveList", curvePointService.findAll());
        log.info("Displayed list of curvePoint items");
        return "curvePoint/list";
    }

    /**
     * Add curve point form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointDto());
        return "curvePoint/add";
    }

    /**
     * Validate.
     *
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointDto curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation failed for curvePoint item");
            return "curvePoint/add";
        }
        curvePointService.create(curvePoint);
        log.info("Successfully created curvePoint");
        return "redirect:/curvePoint/list";
    }

    /**
     * Show update form.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.findById(id));
        return "curvePoint/update";
    }

    /**
     * Update curve point.
     *
     * @param id         the id
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDto curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation before update failed for curvePoint item");
            return "curvePoint/update";
        }
        curvePointService.update(id, curvePoint);
        log.info("Successfully updated curvePoint with ID {}", id);
        return "redirect:/curvePoint/list";
    }

    /**
     * Delete curve point.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        log.info("Request received for /curvePoint/delete endpoint with id {}", id);
        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}