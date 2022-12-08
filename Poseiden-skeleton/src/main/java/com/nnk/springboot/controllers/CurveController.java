package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;

//import javax.validation.Valid;

@Controller
public class CurveController {
    private ICurvePointService curvePointService;

    public CurveController(ICurvePointService curvePointService) {
	this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
	List<CurvePoint> curvePointListResult = new ArrayList<CurvePoint>();
	this.curvePointService.getCurvePoints().forEach(curvePointListResult::add);

	model.addAttribute("curvePoints", curvePointListResult);

	return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
	return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "curvePoint/add";
	}

	this.curvePointService.saveCurvePoint(curvePoint);

	List<CurvePoint> curvePointResult = new ArrayList<CurvePoint>();
	this.curvePointService.getCurvePoints().forEach(curvePointResult::add);

	model.addAttribute("curvePoints", curvePointResult);

	return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	CurvePoint curvePointResult = this.curvePointService.getCurvePointById(id).get();

	model.addAttribute("curvePoint", curvePointResult);

	return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
	    Model model) {
	if (result.hasErrors()) {
	    return "curvePoint/update";
	}
	// Save also updates automatically with Entity Framework
	curvePoint.setCurvePointId(id);
	this.curvePointService.saveCurvePoint(curvePoint);

	// Retrieve the BidList with updated values
	List<CurvePoint> curvePointResult = new ArrayList<CurvePoint>();
	this.curvePointService.getCurvePoints().forEach(curvePointResult::add);

	model.addAttribute("curvePoints", curvePointResult);

	return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
	this.curvePointService.deleteCurvePointById(id);

	List<CurvePoint> curvePointResult = new ArrayList<CurvePoint>();
	this.curvePointService.getCurvePoints().forEach(curvePointResult::add);

	model.addAttribute("curvePoints", curvePointResult);

	return "redirect:/curvePoint/list";
    }
}
