package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.web.controllers.CurveController;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes={CurveController.class})
public class CurvePointControllerTest {

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @MockBean
    private LoginService loginService;

    @Autowired
    private CurveController curveController;

    private final CurvePoint curvePointTest = new CurvePoint(10,20d,30d);
    private final CurvePoint curvePointFail = new CurvePoint(10,20d,30d);
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
    }

    @Test
    public void home(){
        String result1 = curveController.home(model, principal);
        assertEquals("curvePoint/list", result1);
    }

    @Test
    public void addCurveForm(){
        String result2 = curveController.addCurveForm(curvePointTest);
        assertEquals("curvePoint/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = curveController.validate(curvePointTest, result, model);
        assertEquals("curvePoint/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = curveController.validate(curvePointFail, result, model);
        assertEquals("curvePoint/add", result3);
    }

    @Test
    public void showUpdateForm() {
        String result4 = curveController.showUpdateForm(isA(Integer.class), model);
        assertEquals("curvePoint/update", result4);
    }

    @Test
    public void updateCurveOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = curveController.updateCurve(1, curvePointTest, result, model);
        assertEquals("redirect:/curvePoint/list", result5);
    }

    @Test
    public void updateCurveFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = curveController.updateCurve(1,curvePointFail,result,model);
        assertEquals("curvePoint/update", result52);
    }

    @Test
    public void deleteCurve() {
        String result6 = curveController.deleteCurve(isA(Integer.class), model);
        assertEquals("redirect:/curvePoint/list", result6);
    }
}
