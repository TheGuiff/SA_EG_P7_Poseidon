package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.BidList;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.web.controllers.BidListController;
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
@SpringBootTest(classes={BidListController.class})
public class BidListControllerTest {

    @MockBean
    private BidListService bidListService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @Autowired
    private BidListController bidListController;

    private final BidList bidList = new BidList("account","type",20d);
    private final BidList bidListFail = new BidList("account","type",20d);
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(bidListService.getBidList(isA(Integer.class))).thenReturn(Optional.of(bidList));
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
        doNothing().when(bidListService.saveBidList(isA(bidList.getClass())));
    }

    @Test
    public void home(){
        String result1 = bidListController.home(model, principal);
        assertEquals("bidList/list", result1);
    }

    @Test
    public void addBidForm() {
        String result2 = bidListController.addBidForm(bidList);
        assertEquals("bidList/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = bidListController.validate(bidList, result, model);
        assertEquals("bidList/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = bidListController.validate(bidListFail, result, model);
        assertEquals("bidList/add", result3);
    }

    @Test
    public void showUpdateForm() {
        String result4 = bidListController.showUpdateForm(isA(Integer.class), model);
        assertEquals("bidList/update", result4);
    }

    @Test
    public void updateBidOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = bidListController.updateBid(1, bidList, result, model);
        assertEquals("redirect:/bidList/list", result5);
    }

    @Test
    public void updateBidFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = bidListController.updateBid(1, bidListFail, result, model);
        assertEquals("bidList/update", result52);
    }

    @Test
    public void deleteBid() {
        String result6 = bidListController.deleteBid(isA(Integer.class), model);
        assertEquals("redirect:/bidList/list", result6);
    }
}
