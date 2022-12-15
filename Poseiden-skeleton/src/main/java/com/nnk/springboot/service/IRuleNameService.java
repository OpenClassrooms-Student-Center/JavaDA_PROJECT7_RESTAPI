package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;

public interface IRuleNameService {

        /**
         * get all ruleNames
         * @return
         */
        List<RuleName> findAll();

        /**
         * find user by id
         * @param id
         * @return
         */
        RuleName findById(Integer id) throws DataNotFoundException;

        /**
         * save given trade
         * @param ruleName
         */
        void save(RuleName ruleName);



        /**
         * delete given trade
         * @param ruleName
         */
        void delete(RuleName ruleName);
}
