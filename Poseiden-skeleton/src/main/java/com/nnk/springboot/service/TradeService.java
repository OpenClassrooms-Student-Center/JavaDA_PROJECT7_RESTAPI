package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;

import java.util.List;

/**
 * The interface Trade service.
 */
public interface TradeService {
    /**
     * Find all Trade list.
     *
     * @return the list
     */
    List<Trade> findAll();

    /**
     * Find Trade by id.
     *
     * @param id the id
     * @return the trade
     */
    Trade findById(Integer id);

    /**
     * Create trade.
     *
     * @param TradeDto the trade dto
     * @return the trade
     */
    Trade create(TradeDto TradeDto);

    /**
     * Update trade.
     *
     * @param id       the id
     * @param tradeDto the trade dto
     * @return the trade
     */
    Trade update(Integer id, TradeDto tradeDto);

    /**
     * Delete Trade by id.
     *
     * @param id the id
     */
    void delete(Integer id);
}
