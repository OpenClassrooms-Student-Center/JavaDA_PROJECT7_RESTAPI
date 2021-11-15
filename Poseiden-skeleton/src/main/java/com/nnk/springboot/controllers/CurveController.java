package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
 * The type Curve controller.
 */
@Controller
@AllArgsConstructor
public class CurveController {
    private static Logger logger = LoggerFactory.getLogger(CurveController.class);

    /**
     * The Curve point service.
     */

    private CurvePointService curvePointService;

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/curvepoint/list")
    public String list(Model model)
    {
        logger.info("get list");
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvepoint/list";
    }

    /**
     * Add curve point string.
     *
     * @param curvePoint the curve point
     * @return the string
     */
    @GetMapping("/curvepoint/add")
    public String addCurvePoint(CurvePoint curvePoint) {
        return "curvepoint/add";
    }

    /**
     * Validate string.
     *
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvepoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        logger.info("validate curvePoint");
        if (!result.hasErrors()) {
            logger.info("add curvePoint {}", curvePoint);
            List<CurvePoint> curvePoints = curvePointService.saveOrUpdate(curvePoint);
            logger.info("saved curvePoint {}", curvePoint);
            model.addAttribute("curvePoints", curvePoints);
            return "redirect:/curvepoint/list";
        }

        logger.error("curvePoint validate error");
        return "curvepoint/add";
    }

    /**
     * Show update form string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvepoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("find curvePoint by id {}", id);
        model.addAttribute("curvePoint", curvePointService.findById(id));
        return "curvepoint/update";
    }

    /**
     * Update curve point string.
     *
     * @param id         the id
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvepoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        logger.info("update curvePoint {}", curvePoint);
        if (result.hasErrors()) {
            return "curvepoint/update";
        }
        List<CurvePoint> curvePoints = curvePointService.saveOrUpdate(curvePoint);
        model.addAttribute("curvePoints", curvePoints);
        return "redirect:/curvepoint/list";
    }

    /**
     * Delete bid string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvepoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        logger.info("delete curvePoint by Id {}", id);
        List<CurvePoint> curvePoints = curvePointService.delete(id);
        model.addAttribute("curvePoints", curvePoints);
        return "redirect:/curvepoint/list";
    }
}
