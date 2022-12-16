package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface IUserService  {


    User saveUser(UserRegistrationDto registrationDto, String newPassword);

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
     * update given user
     * @param user
     */
    public void update(User user) throws UsernameNotFoundException ;






    /**
     * delete given user
     * @param user
     */
    void delete(User user);
}
