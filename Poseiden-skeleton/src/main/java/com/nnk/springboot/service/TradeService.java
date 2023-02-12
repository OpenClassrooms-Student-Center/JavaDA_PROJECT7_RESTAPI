package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;

import java.util.List;

public interface TradeService {
    List<Trade> findAll();

    Trade findById(Integer id);

    Trade create(TradeDto TradeDto);

    Trade update(Integer id, TradeDto tradeDto);

    void delete(Integer id);
}
