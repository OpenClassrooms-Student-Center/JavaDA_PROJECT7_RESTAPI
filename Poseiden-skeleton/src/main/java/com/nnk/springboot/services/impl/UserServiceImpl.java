package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * The type Trade service.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {
    /**
     * The Repository.
     */
    UserRepository repository;

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return repository;
    }

    @Override
    public User findUserByUsername(String userName) {
        return repository.findUserByUsername(userName);
    }
}
