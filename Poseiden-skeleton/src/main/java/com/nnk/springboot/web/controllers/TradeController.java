package com.nnk.springboot.web.controllers;

import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.TradeService;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@Slf4j
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/trade/list")
    public String home(Model model, Principal user)
    {
        log.info("List of trades");
        try {
            Iterable<Trade> trades = tradeService.getTrades();
            List<Trade> tradeList = new ArrayList<>();
            trades.forEach(tradeList::add);
            model.addAttribute("trades", tradeList);
            model.addAttribute("username", loginService.getGitHub(user));
        } catch (NoSuchElementException e) {
            log.error("/trade/list : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "trade/list";
    }


    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) { //BindingResult ? @Valid ?
        log.info("Add trade");
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.getTrades());
            return "trade/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            log.error("/trade/validate/ : ",errorList.get(0));
            model.addAttribute("error", errorList.get(0));
            return "trade/add";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Update trade by id : " + String.valueOf(id));
        try {
            Optional<Trade> trade = tradeService.getTrade(id);
            model.addAttribute("trade", trade.get());
        } catch (NoSuchElementException e) {
            log.error("/trade/update/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            trade.setTradeId(id);
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.getTrades());
            return "redirect:/trade/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            model.addAttribute("error", errorList.get(0));
            return "trade/update";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("Delete Trade by id : " + String.valueOf(id));
        try {
            tradeService.deleteTrade(id);
            return "redirect:/trade/list";
        } catch (NoSuchElementException e) {
            log.error("/trade/delete/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "trade/list";
        }
    }

}
