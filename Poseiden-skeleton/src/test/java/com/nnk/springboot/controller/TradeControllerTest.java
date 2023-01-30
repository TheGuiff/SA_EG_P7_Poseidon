package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.web.controllers.TradeController;
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
@SpringBootTest(classes={TradeController.class})
public class TradeControllerTest {

    @MockBean
    private TradeService tradeService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @Autowired
    private TradeController tradeController;

    private final Trade trade = new Trade("account","type",20d);
    private final Trade tradeFail = new Trade("account","type",20d);
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(tradeService.getTrade(isA(Integer.class))).thenReturn(Optional.of(trade));
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
        doNothing().when(tradeService.saveTrade(isA(trade.getClass())));
    }

    @Test
    public void home(){
        String result1 = tradeController.home(model, principal);
        assertEquals("trade/list", result1);
    }

    @Test
    public void addTradeForm() {
        String result2 = tradeController.addTradeForm(trade);
        assertEquals("trade/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = tradeController.validate(trade, result, model);
        assertEquals("trade/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = tradeController.validate(tradeFail, result, model);
        assertEquals("trade/add", result3);
    }

    @Test
    public void showUpdateForm() {
        String result4 = tradeController.showUpdateForm(isA(Integer.class), model);
        assertEquals("trade/update", result4);
    }

    @Test
    public void updatetradeOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = tradeController.updateTrade(1, trade, result, model);
        assertEquals("redirect:/trade/list", result5);
    }

    @Test
    public void updatetradeFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = tradeController.updateTrade(1, tradeFail, result, model);
        assertEquals("trade/update", result52);
    }

    @Test
    public void deletetrade() {
        String result6 = tradeController.deleteTrade(isA(Integer.class), model);
        assertEquals("redirect:/trade/list", result6);
    }
}
