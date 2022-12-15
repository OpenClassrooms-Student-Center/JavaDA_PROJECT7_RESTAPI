package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface IUserService extends UserDetailsService {

    /**
     * get all users
     * @return
     */
    List<User> findAll();

    /**
     * find user by id
     * @param id
     * @return
     */
    User findById(Integer id) throws DataNotFoundException;

    /**
     * save given user
     * @param user
     */
    void save(User user);



    /**
     * delete given user
     * @param user
     */
    void delete(User user);
}
