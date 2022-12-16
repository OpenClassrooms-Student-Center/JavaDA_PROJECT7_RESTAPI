package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface ITradeService {

    /**
     * get all users
     * @return
     */
    List<Trade> findAll();

    /**
     * find user by id
     * @param id
     * @return
     */
    Trade findById(Integer id) throws DataNotFoundException;

    /**
     * save given trade
     * @param trade
     */
    void save(Trade trade);

    /**
     * update given trade
     * @param trade
     */
    void update(Trade trade) throws UsernameNotFoundException;

    /**
     * delete given trade
     * @param trade
     */
    void delete(Trade trade);

}
