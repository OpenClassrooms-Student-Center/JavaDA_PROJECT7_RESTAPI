package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;


public interface IUserService  {


    User saveUser(UserRegistrationDto registrationDto, String newPassword);

    /**
     * get all users
     * @return
     */
    List<User> findAll();

    /**
     * find user by id
     *
     * @param id
     * @return
     */
    Optional<User> findById(Integer id) throws DataNotFoundException;

    /**
     * save given user
     * @param user
     */
    void save(User user);


    /**
     * update given user
     *
     * @param user
     * @return
     */
    public User update(User user) throws UsernameNotFoundException ;






    /**
     * delete given user
     * @param user
     */
    void delete(int user);
}
