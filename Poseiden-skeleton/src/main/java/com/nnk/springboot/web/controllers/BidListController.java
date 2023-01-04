package com.nnk.springboot.web.controllers;

import com.nnk.springboot.dal.entity.BidList;
import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        log.info("List of bids");
        try {
            Iterable<BidList> bidLists = bidListService.getBidLists();
            List<BidList> bidListList = new ArrayList<>();
            bidLists.forEach(bidListList::add);
            model.addAttribute("bid", bidLists);
        } catch (NoSuchElementException e) {
            log.error("/bidlist/list : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        log.info("Add bid list");
        if (!result.hasErrors()) {
            bidListService.saveBidList(bid);
            model.addAttribute("bids", bidListService.getBidLists());
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            log.error("/bidList/validate/ : ",errorList.get(0));
            model.addAttribute("error", errorList.get(0));
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Update bid by id : " + String.valueOf(id));
        try {
            Optional<BidList> bidList = bidListService.getBidList(id);
            model.addAttribute("trade", bidList.get());
        } catch (NoSuchElementException e) {
            log.error("/bidList/update/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidList.setBidListId(id);
            bidListService.saveBidList(bidList);
            model.addAttribute("bids", bidListService.getBidLists());
            return "redirect:/bidList/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            model.addAttribute("error", errorList.get(0));
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Delete BidList by id : " + String.valueOf(id));
        try {
            bidListService.deleteBidList(id);
            return "redirect:/bidList/list";
        } catch (NoSuchElementException e) {
            log.error("/bidList/delete/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "bidList/list";
        }
    }

}
