package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class CurveController {
    // TODO DONE: Inject Curve Point services
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO DONE: find all Curve Point, add to model
        List<CurvePoint> curvePoint = curvePointService.findAllCurvePoint();
        model.addAttribute("curvePoint",curvePoint);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO DONE: check data valid and save to db, after saving return Curve list
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        try {
            curvePointService.saveCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            return "curvePoint/add";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: get CurvePoint by Id and to model then show to the form
        if (!curvePointService.checkIfIdExists(id)) {
            return "redirect:/curvePoint/list";
        }
        CurvePoint curvePoint = curvePointService.findByCurvePointId(id);
        model.addAttribute("curvePoint",curvePoint);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO DONE: check required fields, if valid call services to update Curve and return Curve list
        if (!curvePointService.checkIfIdExists(id)) {
            return "redirect:/curvePoint/list";
        }
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        try {
            curvePointService.saveCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }catch (Exception e) {
            return "curvePoint/add";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO DONE: Find Curve by Id and delete the Curve, return to Curve list
        if (!curvePointService.checkIfIdExists(id)) {
            return "redirect:/curvePoint/list";
        }
        CurvePoint curvePoint = curvePointService.findByCurvePointId(id);
        curvePointService.deleteCurvePoint(curvePoint);
        model.addAttribute("curvePoint", curvePointService.findAllCurvePoint());

        return "redirect:/curvePoint/list";
    }
}
