package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface UserRepository extends JpaRepository<UserCustom, Integer>, JpaSpecificationExecutor<UserCustom> {

    public UserCustom findByUsername(String username);

}
