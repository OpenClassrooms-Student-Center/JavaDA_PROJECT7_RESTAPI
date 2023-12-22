package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Override
    boolean existsById(Integer integer);

    Optional<User> findById(Integer id);

}
