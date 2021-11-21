package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;

/**
 * The interface Trade service.
 */
public interface UserService extends CrudService<User> {
    public User findUserByUsername(String userName);
}
