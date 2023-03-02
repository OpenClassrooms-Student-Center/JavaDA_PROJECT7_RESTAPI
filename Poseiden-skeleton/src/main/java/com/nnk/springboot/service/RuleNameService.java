package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;

import java.util.List;

/**
 * The interface Rule name service.
 */
public interface RuleNameService {
    /**
     * Find  all RuleName list.
     *
     * @return the list
     */
    List<RuleName> findAll();

    /**
     * Find RuleName by id.
     *
     * @param id the id
     * @return the rule name
     */
    RuleName findById(Integer id);

    /**
     * Create rule name.
     *
     * @param ruleNameDto the rule name dto
     * @return the rule name
     */
    RuleName create(RuleNameDto ruleNameDto);

    /**
     * Update rule name.
     *
     * @param id          the id
     * @param ruleNameDto the rule name dto
     * @return the rule name
     */
    RuleName update(Integer id, RuleNameDto ruleNameDto);

    /**
     * Delete RuleName by id.
     *
     * @param id the id
     */
    void delete(Integer id);
}
