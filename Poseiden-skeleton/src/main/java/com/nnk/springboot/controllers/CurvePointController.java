package com.nnk.springboot.controllers;

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

@Controller
public class CurvePointController {
    private final CurvePointService curvePointService;

    @Autowired
    public CurvePointController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * This method displays the list of all curvePoints in the database
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * This method displays a form to add a new curvePoint to the database<br>
     * The curvePoint parameter will be overwritten with the form's values
     * @param curvePoint an empty curvePoint object that will receive the results of the form
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * This method adds a curvePoint to the database, after checking its validity<br>
     * Upon resolution it redirects to curvePoint/list
     * @param curvePoint a curvePoint object containing the curvePoint to be added to the database
     * @param result the result of the curvePoint's validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.save(curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * This method displays information about a specific curvePoint<br>
     * The displayed information can be modified
     * @param id the id of the curvePoint to display
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid curvePoint Id:" + id)));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * This method updates a curvePoint in the database, after encrypting its password<br>
     * Upon resolution it redirects to curvePoint/list
     * @param id the id of the curvePoint to update
     * @param curvePoint the new curvePoint attributes
     * @param result the result of the new curvePoint validation
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                              BindingResult result, Model model) {
        if(!result.hasErrors()) {
            curvePointService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
            curvePointService.save(curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "redirect:/curvePoint/update/{id}";
    }

    /**
     * This method deletes a curvePoint from the database<br>
     * Upon resolution it redirects to curvePoint/list
     * @param id the id of the curvePoint to delete
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
