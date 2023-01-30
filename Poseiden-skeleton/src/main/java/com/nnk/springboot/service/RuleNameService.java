package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.RuleName;
import com.nnk.springboot.dal.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public Optional<RuleName> getRuleName (final Integer id) {
        return ruleNameRepository.findById(id);
    }

    public Iterable<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    public  void deleteRuleName(final Integer id) {
        ruleNameRepository.deleteById(id);
    }

    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

}
