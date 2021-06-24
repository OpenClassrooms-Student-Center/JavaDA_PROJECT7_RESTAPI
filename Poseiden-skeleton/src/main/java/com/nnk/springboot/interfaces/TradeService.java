package com.nnk.springboot.interfaces;

import com.nnk.springboot.model.Trade;

public interface TradeService {
    void validateTrade(Trade trade);

    void updateTrade(Integer id, Trade trade);

    void deleteTrade(Integer id);
}
