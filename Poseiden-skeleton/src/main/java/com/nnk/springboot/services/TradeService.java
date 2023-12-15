package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    List<Trade> findAllTrade();

    void saveTrade(Trade trade);

    Trade findTradeById(Integer id);

    boolean checkIfIdExists(int id);

    void deleteTrade(Trade trade);
}
