package com.nnk.springboot.repository;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterAll
    public void initDataBase() {
        userRepository.deleteAll();
    }

    @Test
    public void userTest() {
        User user = new User("Name","password","fullname","USER");

        // Save
        user = userRepository.hashPasswordAndSave(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getUsername().equals("Name"));

        // Update
        user.setRole("ADMIN");
        user = userRepository.hashPasswordAndSave(user);
        Assert.assertTrue(user.getRole().equals("ADMIN"));

        // Find
        List<User> listResult = userRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        Assert.assertFalse(userList.isPresent());
    }
}
