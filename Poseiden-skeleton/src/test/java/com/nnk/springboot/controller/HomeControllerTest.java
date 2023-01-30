package com.nnk.springboot.controller;

import com.nnk.springboot.web.controllers.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes={HomeController.class})
public class HomeControllerTest {

   @MockBean
   private Model model;

   @Autowired
   private HomeController homeController;

    @Test
    public void contextLoads()  {
        assertThat(homeController).isNotNull();
    }

    @Test
    public void homeTest() throws Exception {
        String result = homeController.home(model);
        assertEquals("home", result);
    }

    @Test
    public void adminHomeTest() throws Exception {
        String result = homeController.adminHome();
        assertEquals("redirect:/bidList/list", result);
    }

}
