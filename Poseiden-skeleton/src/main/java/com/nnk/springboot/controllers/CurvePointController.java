package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing CurvePoint operations.
 */
@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

    private final CurvePointService curvePointService;

    /**
     * Constructor for CurvePointController.
     *
     * @param curvePointService the service handling curve point operations
     */
    public CurvePointController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Displays the list of all curve points.
     *
     * @param model the model to hold curve point data
     * @return the curve point list view
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ( UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername() );
        return "curvePoint/list";
    }

    /**
     * Displays the form to add a new curve point.
     *
     * @param curvePoint the curve point object to be added
     * @return the add curve point form view
     */
    @GetMapping("/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new curve point.
     *
     * @param curvePoint the curve point to be validated and saved
     * @param result     the result of validation
     * @param model      the model to hold curve point data
     * @return redirect to the curve point list if successful, otherwise return to the add form
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "curvePoint/add";
        }
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());
        return "redirect:/curvePoint/list";
    }

    /**
     * Displays the form to update an existing curve point.
     *
     * @param id    the id of the curve point to update
     * @param model the model to hold curve point data
     * @return the update curve point form view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePointById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Updates an existing curve point.
     *
     * @param id         the id of the curve point to update
     * @param curvePoint the updated curve point data
     * @param result     the result of validation
     * @param model      the model to hold curve point data
     * @return redirect to the curve point list if successful, otherwise return to the update form
     */
    @PostMapping("/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a curve point.
     *
     * @param id    the id of the curve point to delete
     * @param model the model to hold curve point data
     * @return redirect to the curve point list view
     */
    @GetMapping("/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePointById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
        curvePointService.deleteCurvePointById(id);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());
        return "redirect:/curvePoint/list";
    }
}
