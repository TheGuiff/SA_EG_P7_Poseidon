package com.nnk.springboot.firstUsers;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersProd {

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
    public void usersProd() {
        userRepository.deleteAll();
        bidListRepository.deleteAll();
        curvePointRepository.deleteAll();
        ratingRepository.deleteAll();
        ruleNameRepository.deleteAll();
        tradeRepository.deleteAll();
        User user = new User("Admin","Admin","Administrateur","ADMIN");
        user = userRepository.hashPasswordAndSave(user);
        User userU = new User("User","User","Utilisateur","USER");
        userU = userRepository.hashPasswordAndSave(userU);
        assertEquals(1, 1);
    }

}
