package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.ITradeService;

import java.util.List;

public class TradeService implements ITradeService {
    @Override
    public List<Trade> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) throws DataNotFoundException {
        return null;
    }

    @Override
    public void save(Trade trade) {

    }

    @Override
    public void delete(Trade trade) {

    }
}
