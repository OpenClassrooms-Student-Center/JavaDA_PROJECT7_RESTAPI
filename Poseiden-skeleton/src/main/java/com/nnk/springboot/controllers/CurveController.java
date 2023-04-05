package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private CurvePointService curvePointService;
    public CurveController(CurvePointService curvePointService){
        this.curvePointService = curvePointService;
    }
    // TODO: Inject Curve Point service
//DISPLAY LIST OF CURVEPOINTS PAGE
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }
//DISPLAY ADD CURVEPOINT FORM
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }
//CREATE NEW CURVEPOINT
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(!result.hasErrors()){
            curvePointService.validateNewCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
        // TODO: check data valid and save to db, after saving return Curve list

    }
//DISPLAY CURVEPOINT UPDATE FORM
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }
//UPDATE CURVEPOINT
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint updateCurvePointEntity,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            return "curvePoint/list";
        }
        CurvePoint updatedAndSavedCurvePoint = curvePointService.updateCurvePoint(id, updateCurvePointEntity);
        model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }
//DELETE CURVEPOINT
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}
