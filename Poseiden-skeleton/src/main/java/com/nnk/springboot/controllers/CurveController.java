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
    public String home(Model model) {
        log.info("REQUEST /curvePoint/list");
        model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        log.info("attribute listOfCurvepoints added to Model");
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }
//DISPLAY ADD CURVEPOINT FORM
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        log.info("GET form /curvePoint/add");
        return "curvePoint/add";
    }


//CREATE NEW CURVEPOINT
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("POST /curvePoint/validate");
        if(!result.hasErrors()){
            curvePointService.validateNewCurvePoint(curvePoint);
            log.info("curvePoint validated with id "+ curvePoint.getCurve_id());
            return "redirect:/curvePoint/list";
        }
        log.error("curvepoint to create has errors");
        return "curvePoint/add";
        // TODO: check data valid and save to db, after saving return Curve list

    }
//DISPLAY CURVEPOINT UPDATE FORM
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /curvePoint/update/{id} with id " + id);
            // TODO: get CurvePoint by Id and to model then show to the form
            CurvePoint curvePoint = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePoint", curvePoint);
            log.info("attribute added to Model : curvePoint with id " + id);
            return "curvePoint/update";
        }catch(Exception e){
            log.error("curvePoint form with id "+id+" could not be displayed");
            return "curvePoint/list";
        }
    }
//UPDATE CURVEPOINT
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint updateCurvePointEntity,BindingResult result, Model model) {
        //TODO: changer le retour (revient actuellement sur liste vide)
        log.info("POST /curvePoint/update/{id} with id " + id);
        if (result.hasErrors()) {
            log.error("curvepoint to update has errors");
            return "curvePoint/update";
        }
        try {
            CurvePoint updatedAndSavedCurvePoint = curvePointService.updateCurvePoint(id, updateCurvePointEntity);
            model.addAttribute("listOfCurvepoints", curvePointService.findAll());
            log.info("attribute listOfCurvepoints added to model");
            // TODO: check required fields, if valid call service to update Curve and return Curve list
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.error("curvepoint with id " + id + "could not be update");
            return "curvePoint/list";
        }
    }
//DELETE CURVEPOINT
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /curvePoint/delete/{id} with id " + id);
            curvePointService.deleteCurvePoint(id);
            log.info("curvePoint with id " + id + "deleted");
            //model.addAttribute("listOfCurvepoints", curvePointService.findAll());
            // TODO: Find Curve by Id and delete the Curve, return to Curve list
            return "redirect:/curvePoint/list";
        }catch(Exception e){
            log.error("curvepoint with id "+id+ " could not be deleted");
            return "curvePoint/list";
        }
    }
}
