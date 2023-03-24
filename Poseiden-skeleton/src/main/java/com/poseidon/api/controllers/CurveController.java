package com.poseidon.api.controllers;

import com.poseidon.api.model.CurvePoint;
import com.poseidon.api.model.dto.CurvePointDto;
import com.poseidon.api.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class CurveController {

    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAllCurves());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePointDto curvePointDto) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDto curvePointDto, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            CurvePoint newCurvePoint = curvePointService.convertDtoToEntity(curvePointDto);
            curvePointService.createCurve(newCurvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurves());
            redirectAttributes.addFlashAttribute("message",
                    String.format("Curve Point with id " + newCurvePoint.getId() + " was successfully created"));
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        CurvePoint curvePointToUpdate = curvePointService.findCurvePointById(id);
        CurvePointDto curvePointDto = curvePointService.convertEntityToDto(curvePointToUpdate);
        curvePointDto.setId(id);
        model.addAttribute("curvePointDto", curvePointDto);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Long id, @Valid CurvePointDto curvePointDto,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        CurvePoint updatedCurvePoint = curvePointService.convertDtoToEntity(curvePointDto);
        curvePointService.updateCurve(id, updatedCurvePoint);
        model.addAttribute("curvePoints", curvePointService.findAllCurves());
        redirectAttributes.addFlashAttribute("message",
                String.format("Curve Point with id '%d' was successfully updated", id));
        redirectAttributes.addFlashAttribute("message_type", "alert-primary");

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
            curvePointService.deleteCurve(id);
        redirectAttributes.addFlashAttribute("message",
                String.format("Curve Point with id '%d' was successfully deleted", id));
        model.addAttribute("curvePoints", curvePointService.findAllCurves());
        return "redirect:/curvePoint/list";
    }
}
