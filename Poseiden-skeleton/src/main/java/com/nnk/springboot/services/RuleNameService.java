package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;

import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository){
        this.ruleNameRepository= ruleNameRepository;
    }

    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }



    public RuleName getRuleNameById(Integer i) throws Exception{

        Optional<RuleName> opt= ruleNameRepository.findById(i);
        return opt.get();

    }
    //CREATE NEW RULENAME
    public RuleName validateNewRuleName(RuleName ruleName) throws Exception {
        return ruleNameRepository.save(ruleName);
    }
    //UPDATE RULENAME
    public RuleName updateRuleName(Integer id, RuleName updatedRuleNameEntity) throws Exception{
        Optional<RuleName> opt = ruleNameRepository.findById(id);
        RuleName formerRuleName = opt.get();
        formerRuleName.setName(updatedRuleNameEntity.getName());
        formerRuleName.setDescription(updatedRuleNameEntity.getDescription());
        formerRuleName.setJson(updatedRuleNameEntity.getJson());
        formerRuleName.setTemplate(updatedRuleNameEntity.getTemplate());
        formerRuleName.setSql_str(updatedRuleNameEntity.getSql_str());
        formerRuleName.setSql_part(updatedRuleNameEntity.getSql_part());
        // TODO: check required fields, if valid call service to update RuleName and return list RuleName
        return ruleNameRepository.save(formerRuleName);

    }
    //DELETE RULENAME
    public void deleteRuleName(Integer id) throws Exception{
        Optional<RuleName> opt = ruleNameRepository.findById(id) ;
        RuleName ruleNameToDelete = opt.get();
        ruleNameRepository.delete(ruleNameToDelete);
    }

}
