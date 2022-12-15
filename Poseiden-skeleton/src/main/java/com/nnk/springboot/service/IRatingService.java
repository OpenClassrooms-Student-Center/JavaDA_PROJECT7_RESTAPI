package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;

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
     * delete given rating
     * @param rating
     */
    void delete(Rating rating);

}
