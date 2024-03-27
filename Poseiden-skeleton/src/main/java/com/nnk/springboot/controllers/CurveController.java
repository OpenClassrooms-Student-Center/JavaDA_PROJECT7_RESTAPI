package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
public class CurveController {
    private CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request){  // find all Curve Point, add to model
        model.addAttribute("curvePoints", curvePointService.findAll());
        model.addAttribute("request", request);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curve) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) { // check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
            curvePointService.saveCurve(curvePoint);
            return "redirect:list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {  // get CurvePoint by Id and to model then show to the form
        model.addAttribute("curvePoint", curvePointService.findCurveById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, // check required fields, if valid call service to update Curve and return Curve list
                            BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.saveCurve(curvePoint);
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) { // Find Curve by Id and delete the Curve, return to Curve list
        curvePointService.deleteCurve(curvePointService.findCurveById(id));
        return "redirect:/curvePoint/list";
    }
}
