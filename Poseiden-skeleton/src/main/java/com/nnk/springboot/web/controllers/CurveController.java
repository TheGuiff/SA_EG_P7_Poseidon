package com.nnk.springboot.web.controllers;

import com.nnk.springboot.dal.entity.CurvePoint;
import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        log.info("List of curve points");
        try {
            Iterable<CurvePoint> curvePoints = curvePointService.getCurvedPoints();
            List<CurvePoint> curvePointList = new ArrayList<>();
            curvePoints.forEach(curvePointList::add);
            model.addAttribute("curves", curvePointList);
        } catch (NoSuchElementException e) {
            log.error("/curvePoint/list : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("Add curve point");
        if (!result.hasErrors()) {
           curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curves", curvePointService.getCurvedPoints());
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            log.error("/curvePoint/validate/ : ",errorList.get(0));
            model.addAttribute("error", errorList.get(0));
        }
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Update curve point by id : " + String.valueOf(id));
        try {
            Optional<CurvePoint> curvePoint = curvePointService.getCurvePoint(id);
            model.addAttribute("curvePoint", curvePoint.get());
        } catch (NoSuchElementException e) {
            log.error("/curvePoint/update/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePoint.setCurveId(id);
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curves", curvePointService.getCurvedPoints());
            return "redirect:/curvePoint/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            model.addAttribute("error", errorList.get(0));
            return "curvePoint/update";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Delete Curve Point by id : " + String.valueOf(id));
        try {
            curvePointService.deleteCurvePoint(id);
            return "redirect:/curvePoint/list";
        } catch (NoSuchElementException e) {
            log.error("/curvePoint/delete/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "curvePoint/list";
        }
    }
}
