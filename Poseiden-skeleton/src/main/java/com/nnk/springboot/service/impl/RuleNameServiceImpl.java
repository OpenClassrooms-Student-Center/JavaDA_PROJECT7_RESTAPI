package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.IRuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for RuleNameService
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("RuleNameServiceImpl");

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleName findById(Integer id) throws DataNotFoundException {
        return ruleNameRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(RuleName ruleName) throws UsernameNotFoundException {
        logger.debug("update ruleName:{}", ruleName.getName());
        Optional<RuleName> isAlreadyUser = ruleNameRepository.findById(ruleName.getId().intValue());
        if (isAlreadyUser.isPresent()) {
            ruleNameRepository.save(ruleName);
        } else {
            throw new UsernameNotFoundException("No ruleName " + ruleName + " present in dataBase ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
