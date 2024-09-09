package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.CurvePointService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/curvePoint")
@RequiredArgsConstructor
public class CurveController {
    // Inject Curve Point service
    private final CurvePointService curvePointService;

    /**
     * Find all Curve Point, add to model
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/list")
    public String home(Model model,@AuthenticationPrincipal User user)
    {
        model.addAttribute("remoteUser", user.getUsername());
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
        return "curvePoint/list";
    }

    /**
     * Add new curve point
     * @param bid
     * @return
     */
    @GetMapping("/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    /**
     * Validate curve point
     * @param curvePoint
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // Check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
        }
        return "curvePoint/add";
    }

    /**
     * Show form to update curve point
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get CurvePoint by Id and to model then show to the form
        try {
            CurvePoint curvePoint = curvePointService.findCurvePointById(id);
            curvePoint.setCurveId(id);
            model.addAttribute("curvePoint", curvePoint);
        } catch (RequestedObjectNotFoundException e) {
            // Handle exception
            model.addAttribute("errorMsg", e.getMessage());
            return "404";
        }
        return "curvePoint/update";
    }

    /**
     * Update curve point
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // Check required fields, if valid call service to update Curve and return Curve list
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
        }
        return "redirect:/curvePoint/list";
    }

    /**
     * Delete curve point
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteByid(@PathVariable("id") Integer id, Model model) {
       curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}

