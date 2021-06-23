package com.nnk.springboot.controllers;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.interfaces.CurveService;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Slf4j
@AllArgsConstructor
@Controller
public class CurveController {

    private final CurvePointRepository curvePointRepository;
    private final CurveService curveService;


    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        log.info("home: show curvePoint/list");
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm() {
        log.info("addCurveForm: show curvePoint/add"  );
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint) {
        log.info("validate: CurveId; " + curvePoint.getCurveId() + " Term; " + curvePoint.getTerm() + " Value; " + curvePoint.getValue());
        curveService.validateCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("showUpdateForm: " + id);
        model.addAttribute("curvePoint", curvePointRepository.findCurvePointById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("updateBid: " + id);
        if(result.hasErrors()){
            redirectAttributes.addAttribute("error", true);
        }
        curveService.updateCurvePoint(id, curvePoint);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        log.info("deleteBid:" + id);
        curveService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
