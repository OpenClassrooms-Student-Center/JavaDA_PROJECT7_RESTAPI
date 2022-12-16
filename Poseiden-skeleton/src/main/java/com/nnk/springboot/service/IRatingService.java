package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IRatingService {


    /**
     * get all rating
     * @return
     */
    List<Rating> findAll();

    /**
     * find rating by id
     * @param id
     * @return
     */
    Rating findById(Integer id) throws DataNotFoundException;

    /**
     * save given rating
     * @param rating
     */
    void save(Rating rating);

    /**
     * update rating user
     * @param rating
     */
    void update(Rating rating) throws UsernameNotFoundException;

    /**
     * delete given rating
     * @param rating
     */
    void delete(Rating rating);

}
