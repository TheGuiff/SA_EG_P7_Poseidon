package com.nnk.springboot.web.controllers;

import com.nnk.springboot.dal.entity.RuleName;
import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreInsertEvent;
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

@Slf4j
@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal user)
    {
        log.info("List of rulenames");
        try {
            Iterable<RuleName> ruleNames = ruleNameService.getRuleNames();
            List<RuleName> ruleNameList = new ArrayList<>();
            ruleNames.forEach(ruleNameList::add);
            model.addAttribute("rules", ruleNames);
            model.addAttribute("username", loginService.getGitHub(user));
        } catch (NoSuchElementException e) {
            log.error("/ruleName/list : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("Add ruleName");
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("rules", ruleNameService.getRuleNames());
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            log.error("/ruleName/validate/ : ",errorList.get(0));
            model.addAttribute("error", errorList.get(0));
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Update ruleName by id : " + String.valueOf(id));
        try {
            Optional<RuleName> ruleName = ruleNameService.getRuleName(id);
            model.addAttribute("ruleName", ruleName.get());
        } catch (NoSuchElementException e) {
            log.error("/ruleName/update/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleName.setId(id);
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("rules", ruleNameService.getRuleNames());
            return "redirect:/ruleName/list";
        } else {
            List<ObjectError> errorList = result.getAllErrors();
            model.addAttribute("error", errorList.get(0));
            return "ruleName/update";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        log.info("Delete RuleName by id : " + String.valueOf(id));
        try {
            ruleNameService.deleteRuleName(id);
            return "redirect:/ruleName/list";
        } catch (NoSuchElementException e) {
            log.error("/ruleName/delete/" + String.valueOf(id) + " : ",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "ruleName/list";
        }
    }
}
