package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    //TODO : JpaSpecificationExecutor à l'air d'être un outil pour faire des recherches avancé dans la base de donnée.
    //  Faire plus de recherches.
    public User findByUsername(String username);

}
