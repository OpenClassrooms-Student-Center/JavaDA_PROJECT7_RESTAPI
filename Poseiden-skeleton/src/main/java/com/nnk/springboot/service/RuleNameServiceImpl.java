package com.nnk.springboot.service;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(Integer id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        return ruleName.orElseThrow(() -> new NotFoundException("RuleName not found with id " + id));
    }

    @Override
    public RuleName create(RuleNameDto ruleNameDto) {
        RuleName ruleName = new RuleName(ruleNameDto);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(Integer id, RuleNameDto ruleNameDto) {
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
        RuleName ruleName = findById(id);
        ruleNameRepository.delete(ruleName);
    }
}
