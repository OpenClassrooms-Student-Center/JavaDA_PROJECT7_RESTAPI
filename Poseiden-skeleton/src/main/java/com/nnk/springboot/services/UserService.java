package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

    /**
     * Return a list of all the Users
     * @return
     */
    List<User> getAllUsers();

    /**
     * Check if user exists with username
     * @param username
     * @return
     */
    boolean checkIfUserExistsByUsername(String username);

    /**
     *
     * @param id
     * @return
     */
    User getUserById(int id);
}
