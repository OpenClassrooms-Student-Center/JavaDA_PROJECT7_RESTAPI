package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IRuleNameService {

        /**
         * get all ruleNames
         * @return
         */
        List<RuleName> findAll();

        /**
         * find user by id
         *
         * @param id
         * @return
         */
        Optional<RuleName> findById(Integer id) throws DataNotFoundException;

        /**
         * save given trade
         * @param ruleName
         */
        void save(RuleName ruleName);


        /**
         * update given ruleName
         * @param ruleName
         */
        public void update(RuleName ruleName) throws UsernameNotFoundException;


        /**
         * delete given trade
         * @param ruleName
         */
        void delete(RuleName ruleName);
}
