package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    /**
     * @return
     */
    List<Trade> findAllTrade();

    /**
     * @param trade
     */
    void saveTrade(Trade trade);

    /**
     * @param id
     * @return
     */
    Trade findTradeById(Integer id);

    /**
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * @param trade
     */
    void deleteTrade(Trade trade);
}
