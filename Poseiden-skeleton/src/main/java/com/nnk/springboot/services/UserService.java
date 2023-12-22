package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

    /**
     * @return
     */
    List<User> getAllUsers();

    /**
     * @param username
     * @return
     */
    boolean checkIfUserExistsByUsername(String username);

    /**
     * @param id
     * @return
     */
    User getUserById(int id);
}
