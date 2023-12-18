package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return ruleNameRepository.findById(id);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));
        ruleNameRepository.deleteById(id);
    }

    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }
}
