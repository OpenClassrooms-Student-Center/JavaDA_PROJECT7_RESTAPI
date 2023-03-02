package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService extends UserDetailsService {
    /**
     * Find all User list.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Find User by id.
     *
     * @param id the id
     * @return the user
     */
    User findById(Integer id);

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    User create(User user);

    /**
     * Update user.
     *
     * @param id   the id
     * @param user the user
     * @return the user
     */
    User update(Integer id, User user);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Integer id);
}
