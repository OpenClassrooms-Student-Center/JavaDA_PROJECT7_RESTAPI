package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all RuleName related business logic.
 */
@Service
@Slf4j
public class RuleNameServiceImpl implements RuleNameService {
    private final RuleNameRepository ruleNameRepository;

    /**
     * Instantiates a new Rule name service.
     *
     * @param ruleNameRepository the rule name repository
     */
    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAll() {
        log.info("Retrieving all RuleNames");
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(Integer id) {
        log.info("Retrieving RuleName with id {}", id);
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        return ruleName.orElseThrow(() -> new NotFoundException("RuleName not found with id " + id));
    }

    @Override
    public RuleName create(RuleNameDto ruleNameDto) {
        log.info("Creating new RuleName");
        RuleName ruleName = new RuleName(ruleNameDto);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(Integer id, RuleNameDto ruleNameDto) {
        log.info("Updating RuleName with id {}", id);
        RuleName ruleName = findById(id);
        ruleName.setName(ruleNameDto.getName());
        ruleName.setDescription(ruleNameDto.getDescription());
        ruleName.setJson(ruleNameDto.getJson());
        ruleName.setSqlStr(ruleNameDto.getSqlStr());
        ruleName.setTemplate(ruleNameDto.getTemplate());
        ruleName.setSqlPart(ruleNameDto.getSqlPart());
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting RuleName with id {}", id);
        RuleName ruleName = findById(id);
        ruleNameRepository.delete(ruleName);
    }
}
