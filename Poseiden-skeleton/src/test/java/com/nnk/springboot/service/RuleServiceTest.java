package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.RuleName;
import com.nnk.springboot.dal.repositories.RuleNameRepository;
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
public class RuleServiceTest {

    @Autowired
    RuleNameRepository ruleNameRepository;

    @Autowired
    RuleNameService ruleNameService;

    private final List<RuleName> ruleNames = new ArrayList<>();
    private RuleName ruleName;

    @Test
    public void ruleNameTest() {
        ruleNameRepository.deleteAll();

        RuleName ruleName1 = new RuleName("name1","description","json","template","sql","sqlPart");
        ruleNameRepository.save(ruleName1);
        RuleName ruleName2 = new RuleName("name2","description","json","template","sql","sqlPart");
        ruleNameRepository.save(ruleName2);
        RuleName ruleName3 = new RuleName("name3","description","json","template","sql","sqlPart");

        //get all
        Iterable<RuleName> ruleNameIterable = ruleNameService.getRuleNames();
        ruleNameIterable.forEach(ruleNames::add);
        assertEquals(2,ruleNames.size());

        //get by id
        ruleName = ruleNames.get(0);
        Integer id = ruleName.getId();
        Optional<RuleName> ruleNameOptional = ruleNameService.getRuleName(id);
        assertTrue(ruleNameOptional.isPresent());

        //save
        ruleName = ruleNameService.saveRuleName(ruleName3);
        Assert.assertNotNull(ruleName.getId());
        assertEquals("name3", ruleName.getName());

        //delete
        ruleNameService.deleteRuleName(id);
        ruleNameOptional = ruleNameRepository.findById(id);
        Assert.assertFalse(ruleNameOptional.isPresent());

        ruleNameRepository.deleteAll();
    }
}
