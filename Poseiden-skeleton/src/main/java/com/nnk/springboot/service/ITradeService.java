package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ITradeService {

    /**
     * get all users
     *
     * @return
     */
    List<Trade> findAll();

    /**
     * find user by id
     *
     * @param id
     * @return
     */
    Optional<Trade> findById(Integer id) throws DataNotFoundException;

    /**
     * save given trade
     *
     * @param trade
     * @return
     */
    Trade save(Trade trade);

    /**
     * update given trade
     *
     * @param trade
     * @return
     */
    Trade update(Trade trade) throws UsernameNotFoundException;

    /**
     * delete given trade
     *
     * @param trade
     */
    void delete(Integer trade);

}
