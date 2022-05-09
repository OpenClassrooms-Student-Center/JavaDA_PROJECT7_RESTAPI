package com.nnk.springboot.controllers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurveController {

	@Autowired
	private CurvePointService curvePointService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePointList", curvePointService.findAll());
        LOGGER.info("Curvepoint List");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
    	model.addAttribute("curvePoint", new CurvePoint());
		LOGGER.info("CurvePoint add form");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
        	return "curvePoint/add";
        } else {
        curvePointService.createCurvePoint(curvePoint);
		LOGGER.info("CurvePoint added");
        return "redirect:/curvePoint/list";
        }      
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.findById(id);
		LOGGER.info("CurvePoint to update found");
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			LOGGER.debug("Invalid fields provided");
			return "curvePoint/update";
		} else {
			try {
				curvePointService.updateCurvePoint(curvePoint, id);
				LOGGER.info("CurvePoint updated");
			} catch (EntityNotFoundException e) {
				model.addAttribute("message", e.getMessage());
				LOGGER.error("This curvepoint doesn't exist");
			}
			return "redirect:/curvePoint/list";
		}
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
		LOGGER.info("Deleting Curvepoint with id:" + id);
    	curvePointService.deleteById(id);
    	return "redirect:/curvePoint/list";
    }
}
