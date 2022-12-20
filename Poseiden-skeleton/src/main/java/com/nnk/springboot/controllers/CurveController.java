package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.ICurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger("BidListController");

    private ICurvePointService curvePointService;

    public CurveController(ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }


    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal) {
        logger.info("@RequestMapping(\"/curvePointList\")");
        model.addAttribute("sortedList", curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        logger.info("@GetMapping(\"/curvePoint/add\")");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        logger.info("@PostMapping(\"/curvePoint/validate\")");
        /**form data validation*/
        if (result.hasErrors()) {
            return "/curvePoint/add";
        }
        /**save in to dataBase:*/
        curvePointService.save(curvePoint);
        //redirection do not use the current Model
        return "redirect:curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("@GetMapping(\"/curvePoint/update/{id}\")");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        logger.info("@PostMapping(\"/curvePoint/update/{id}\")");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
       CurvePoint curvePoint = curvePointService.findById(id);
       curvePointService.delete(curvePoint);
       model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}
