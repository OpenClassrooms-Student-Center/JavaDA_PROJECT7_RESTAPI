package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {
    /**
     * Return a list of all the RuleName
     * @return List of RuleName
     */
    List<RuleName> findAllRuleName();

    /**
     * save the ruleName in db
     * @param ruleName
     */
    void saveRuleName(RuleName ruleName);

    /**
     * find the ruleName in db
     * @param id
     * @return
     */
    Optional<RuleName> findRuleNameById(Integer id);

    /**
     * find the ruleName in db
     * @param id
     * @return
     */
    RuleName findByRuleNameId(Integer id);

    /**
     * Check if ID exists
     *
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * delete ruleName in db
     * @param ruleName
     */
    void deleteRuleName(RuleName ruleName);
}
