package com.poseidon.api.service;

import com.poseidon.api.model.Rule;
import com.poseidon.api.model.dto.RuleDto;
import com.poseidon.api.repositories.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RuleService {

    @Autowired
    RuleRepository ruleNameRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Rule> findAllRules() {
        return ruleNameRepository.findAll();
    }

    public Rule findRuleById(Long id) {
        Optional<Rule> ruleName = ruleNameRepository.findRuleById(id);
        if (id != null && ruleName.isPresent()) {
            return ruleName.get();
        }
        throw new UsernameNotFoundException("Could not find rule with id : " + id);
    }

    public boolean createRule(Rule ruleEntity) {
        if (ruleEntity != null && !ruleNameRepository.findRuleById(ruleEntity.getId()).isPresent()) {
            ruleNameRepository.save(ruleEntity);
            log.info("[RULE SERVICE] Created a new rule with id '{}' and name '{}'", ruleEntity.getId(),
                    ruleEntity.getName());
            return true;
        }
        throw new UsernameNotFoundException("There was an error while creating the rule");
    }

    public boolean updateRule(Long id, Rule ruleEntityUpdated) {
        Optional<Rule> ruleName = ruleNameRepository.findRuleById(id);
        if (id != null && ruleName.isPresent()) {
            ruleEntityUpdated.setId(id);
            ruleNameRepository.save(ruleEntityUpdated);

            log.info("[RULE SERVICE] Updated rule id '{}' with name '{}'", ruleEntityUpdated.getId(),
                    ruleEntityUpdated.getName());
            return true;
        }
        throw new UsernameNotFoundException("Could not find rule with id : " + id);
    }

    public boolean deleteRule(Long id) {
        Optional<Rule> ruleName = ruleNameRepository.findRuleById(id);
        if (id != null && ruleName.isPresent()) {
            ruleNameRepository.delete(ruleName.get());
            log.info("[RULE SERVICE] Deleted rule id '{}'", id);
            return true;
        }
        throw new UsernameNotFoundException("Could not find rule with id : " + id);
    }

    public Rule convertDtoToEntity(RuleDto ruleDto) {
        return modelMapper.map(ruleDto, Rule.class);
    }

    public RuleDto convertEntityToDto(Rule ruleEntity) {
        return modelMapper.map(ruleEntity, RuleDto.class);
    }
}
