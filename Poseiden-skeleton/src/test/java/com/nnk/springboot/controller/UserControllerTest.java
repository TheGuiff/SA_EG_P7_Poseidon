package com.nnk.springboot.controller;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.UserRepository;
import com.nnk.springboot.service.LoginService;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.web.controllers.UserController;
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
@SpringBootTest(classes={UserController.class})
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LoginService loginService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult result;

    @MockBean
    private Principal principal;

    @Autowired
    private UserController userController;

    private final User user = new User("name","Pasword:1234","fullname","role");
    private final User userFail = new User("name2","Pasword:1234","fullname","role");
    private final User userFail2 = new User("name2","Pas","fullname","role");
    private final ObjectError objectError = new ObjectError("errortest","errortest");
    private List<ObjectError> errorList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        when(userService.getUser(isA(Integer.class))).thenReturn(Optional.of(user));
        when(loginService.getGitHub(isA(Principal.class))).thenReturn("usertest");
        doNothing().when(userService.saveUser(isA(user.getClass())));
    }

    @Test
    public void home(){
        String result1 = userController.home(model);
        assertEquals("user/list", result1);
    }

    @Test
    public void adduserForm() {
        String result2 = userController.addUser(user);
        assertEquals("user/add", result2);
    }

    @Test
    public void validateOK() {
        when(result.hasErrors()).thenReturn(false);
        String result3 = userController.validate(user, result, model);
        assertEquals("redirect:/user/list", result3);
    }

    @Test
    public void validateKO() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result3 = userController.validate(userFail, result, model);
        assertEquals("user/add", result3);
    }

    @Test
    public void showUpdateForm() {
        when(userRepository.findById(isA(Integer.class))).thenReturn(Optional.of(user));
        String result4 = userController.showUpdateForm(isA(Integer.class), model);
        assertEquals("user/update", result4);
    }

    @Test
    public void updateuserOK() {
        when(result.hasErrors()).thenReturn(false);
        String result5 = userController.updateUser(1, user, result, model);
        assertEquals("redirect:/user/list", result5);
    }

    @Test
    public void updateuserFail() {
        errorList.add(objectError);
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(errorList);
        String result52 = userController.updateUser(1, userFail, result, model);
        assertEquals("user/update", result52);
    }

    @Test
    public void updateuserFailedPassword() {
        String result53 = userController.updateUser(1, userFail2, result, model);
        assertEquals("redirect:/user/list", result53);
    }

    @Test
    public void deleteuser() {
        when(userRepository.findById(isA(Integer.class))).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(isA(user.getClass()));
        String result6 = userController.deleteUser(1,model);
        assertEquals("redirect:/user/list", result6);
    }

}
