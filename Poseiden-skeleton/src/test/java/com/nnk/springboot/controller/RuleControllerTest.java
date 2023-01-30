package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.RuleName;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.web.controllers.RuleNameController;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes={RuleNameController.class})
public class RuleControllerTest {

    @MockBean
    private RuleNameService ruleService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @Autowired
    private RuleNameController ruleController;

    private final RuleName rule = new RuleName("name","description","json","teù^late","sql","sqlp");
    private final RuleName ruleFail = new RuleName("namefail","description","json","teù^late","sql","sqlp");
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(ruleService.getRuleName(isA(Integer.class))).thenReturn(Optional.of(rule));
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
        doNothing().when(ruleService.saveRuleName(isA(rule.getClass())));
    }

    @Test
    public void home(){
        String result1 = ruleController.home(model, principal);
        assertEquals("ruleName/list", result1);
    }

    @Test
    public void addRuleForm() {
        String result2 = ruleController.addRuleForm(rule);
        assertEquals("ruleName/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = ruleController.validate(rule, result, model);
        assertEquals("ruleName/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = ruleController.validate(ruleFail, result, model);
        assertEquals("ruleName/add", result3);
    }

    @Test
    public void showUpdateForm() {
        String result4 = ruleController.showUpdateForm(isA(Integer.class), model);
        assertEquals("ruleName/update", result4);
    }

    @Test
    public void updateRuleOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = ruleController.updateRuleName(1, rule, result, model);
        assertEquals("redirect:/ruleName/list", result5);
    }

    @Test
    public void updateRuleFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = ruleController.updateRuleName(1, ruleFail, result, model);
        assertEquals("ruleName/update", result52);
    }

    @Test
    public void deleteRule() {
        String result6 = ruleController.deleteRuleName(isA(Integer.class), model);
        assertEquals("redirect:/ruleName/list", result6);
    }

}
