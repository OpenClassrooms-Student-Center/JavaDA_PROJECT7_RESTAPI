package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
