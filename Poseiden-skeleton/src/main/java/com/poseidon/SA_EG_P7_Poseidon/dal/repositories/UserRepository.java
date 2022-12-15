package com.poseidon.SA_EG_P7_Poseidon.dal.repositories;

import com.poseidon.SA_EG_P7_Poseidon.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
