package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    List<RuleName> findAll();

    RuleName save(RuleName ruleName);

    void delete(RuleName ruleName);

    Optional<RuleName> findById(Integer id);

    @Override
    boolean existsById(Integer integer);
}
