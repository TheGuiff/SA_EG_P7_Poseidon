package com.nnk.springboot.web.controllers;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        log.info("List of rating");
        try {
            Iterable<Rating> ratings = ratingService.getRatings();
            List<Rating> ratingList = new ArrayList<>();
            ratings.forEach(ratingList::add);
            model.addAttribute("ratings", ratings);
        } catch (NoSuchElementException e) {
            log.error("/rating/list : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("Add rating");
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.getRatings());
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            log.error("/rating/validate/ : ",errorList.get(0));
            model.addAttribute("error", errorList.get(0));
        }
        return "rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Update rating by id : " + String.valueOf(id));
        try {
            Optional<Rating> rating = ratingService.getRating(id);
            model.addAttribute("rating", rating.get());
        } catch (NoSuchElementException e) {
            log.error("/rating/update/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            rating.setId(id);
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.getRatings());
            return "redirect:/rating/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            model.addAttribute("error", errorList.get(0));
            return "rating/update";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("Delete Rating by id : " + String.valueOf(id));
        try {
            ratingService.deleteRating(id);
            return "redirect:/rating/list";
        } catch (NoSuchElementException e) {
            log.error("/rating/delete/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "rating/list";
        }
    }
}
