package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private final List<User> users = new ArrayList<>();
    private User user;

    @Test
    public void userTest() {
        userRepository.deleteAll();

        User user1 = new User("Name1","password1","FullName1","USER");
        userRepository.hashPasswordAndSave(user1);
        User user2 = new User("Name2","password2","FullName2","USER");
        userRepository.hashPasswordAndSave(user2);
        User user3 = new User("Name3","password3","FullName3","USER");

        //get all
        Iterable<User> userIterable = userService.getUsers();
        userIterable.forEach(users::add);
        assertEquals(2,users.size());

        //get by id
        user = users.get(0);
        Integer id = user.getId();
        Optional<User> userOptional = userService.getUser(id);
        assertTrue(userOptional.isPresent());

        //save
        user = userService.saveUser(user3);
        Assert.assertNotNull(user.getId());
        assertEquals("Name3", user.getUsername());

        //delete
        userService.deleteUser(id);
        userOptional = userRepository.findById(id);
        Assert.assertFalse(userOptional.isPresent());

        userRepository.deleteAll();
    }

}
