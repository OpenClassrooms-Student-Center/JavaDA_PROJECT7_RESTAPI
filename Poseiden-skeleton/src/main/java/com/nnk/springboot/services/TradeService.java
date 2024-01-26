package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    /**
     * Return a list of all the Trade
     * @return List of Trade
     */
    List<Trade> findAllTrade();

    /**
     * save the trade in db
     * @param trade
     */
    void saveTrade(Trade trade);

    /**
     * find the trade in db with trade id
     * @param id
     * @return
     */
    Trade findTradeById(Integer id);

    /**
     * Check if ID exists
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * Delete the Trade in db
     *
     * @param trade
     */
    void deleteTrade(Trade trade);
}
