package com.nnk.springboot.firstUsers;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsersTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidListRepository bidListRepository;

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RuleNameRepository ruleNameRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Test
    public void dumTest() {
        userRepository.deleteAll();
        bidListRepository.deleteAll();
        curvePointRepository.deleteAll();
        ratingRepository.deleteAll();
        ruleNameRepository.deleteAll();
        tradeRepository.deleteAll();
        User user = new User();
        user.setUsername("Toto");
        user.setFullname("Toto admin");
        user.setPassword("1234");
        user.setRole("ADMIN");
        user = userRepository.hashPasswordAndSave(user);
        User user2 = new User();
        user2.setUsername("Titi");
        user2.setFullname("Titi user");
        user2.setPassword("1234");
        user2.setRole("USER");
        user2 = userRepository.hashPasswordAndSave(user2);
        assertEquals(1, 1);
    }
}
