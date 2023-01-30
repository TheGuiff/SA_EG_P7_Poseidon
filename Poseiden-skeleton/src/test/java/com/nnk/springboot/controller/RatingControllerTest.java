package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.web.controllers.RatingController;
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
@SpringBootTest(classes={RatingController.class})
public class RatingControllerTest {

    @MockBean
    private RatingService ratingService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @Autowired
    private RatingController ratingController;

    private final Rating rating = new Rating("one","two","AAA",18);
    private final Rating ratingFail = new Rating("onebis","two","AAA",18);
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(ratingService.getRating(isA(Integer.class))).thenReturn(Optional.of(rating));
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
        doNothing().when(ratingService.saveRating(isA(rating.getClass())));
    }

    @Test
    public void home(){
        String result1 = ratingController.home(model, principal);
        assertEquals("rating/list", result1);
    }

    @Test
    public void addRatingForm() {
        String result2 = ratingController.addRatingForm(rating);
        assertEquals("rating/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = ratingController.validate(rating, result, model);
        assertEquals("rating/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = ratingController.validate(ratingFail, result, model);
        assertEquals("rating/add", result3);
    }

    @Test
    public void showUpdateForm() {
        String result4 = ratingController.showUpdateForm(isA(Integer.class), model);
        assertEquals("rating/update", result4);
    }

    @Test
    public void updateBidOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = ratingController.updateRating(1, rating, result, model);
        assertEquals("redirect:/rating/list", result5);
    }

    @Test
    public void updateBidFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = ratingController.updateRating(1, ratingFail, result, model);
        assertEquals("rating/update", result52);
    }

    @Test
    public void deleteBid() {
        String result6 = ratingController.deleteRating(isA(Integer.class), model);
        assertEquals("redirect:/rating/list", result6);
    }
}
