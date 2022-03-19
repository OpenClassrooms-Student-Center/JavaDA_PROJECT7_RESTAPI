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

import javax.validation.Valid;

@Controller
public class CurvePointController {
    @Autowired
    private CurvePointService curvePointService;

    /**
	 * 
	 * displays CurvePoint list retrieved from database
	 * 
	 */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        return "curvePoint/list";
    }

    /**
	 * displays CurvePoint to add
	 */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
	 * validates new CurvePoint
	 * 
	 * @param curvePoint: CurvePoint to create and save
	 * @param result:     form result to validate
	 * @return CurvePoint list displayed if validated, new add form with errors
	 *         displayed else
	 */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    	if(!result.hasErrors()) {
			curvePointService.saveCurvePoint(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
			return "redirect:/curvePoint/list";
		}
        return "curvePoint/add";
    }

    /**
	 * displays CurvePoint form to update
	 * 
	 * @param id : id of the CurvePoint to update
	 * @return return CurvePoint form to update
	 */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curvePoint = curvePointService.findCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
	 * validates updated CurvePoint
	 * 
	 * @param id      : id of the CurvePoint to update
	 * @param curvePoint : CurvePoint to validate
	 * @param result  : form result to validate
	 * @return CurvePoint list with this CurvePoint updated, CurvePoint to
	 *         update form with errors displayed else
	 */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	if(result.hasErrors()) {
			return "/curvePoint/update";
		}
		curvePoint.setId(id);
		curvePointService.saveCurvePoint(curvePoint);
		model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    /**
	 * deletes selected CurvePoint from database
	 * 
	 * @param id: id of the CurvePoint to delete
	 * @return the CurvePoint list after selected CurvePoint deleted
	 */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curvePoint = curvePointService.findCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		curvePointService.deleteCurvePoint(curvePoint);
		model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
