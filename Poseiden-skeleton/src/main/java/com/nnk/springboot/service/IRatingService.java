package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IRatingService {


    /**
     * get all rating
     * @return
     */
    List<Rating> findAll();

    /**
     * find rating by id
     *
     * @param id
     * @return
     */
    Optional<Rating> findById(Integer id) throws DataNotFoundException;

    /**
     * save given rating
     *
     * @param rating
     * @return
     */
    Rating save(Rating rating);

    /**
     * update rating user
     *
     * @param rating
     * @return
     */
    Rating update(Rating rating) throws DataNotFoundException;

    /**
     * delete given rating by rating id
     * @param rating
     */
    void delete(Integer rating);

}
