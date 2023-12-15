package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Trade> findAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void saveTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Trade findTradeById(Integer id){
        return tradeRepository.findByTradeId(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean checkIfIdExists(int id) {
        return tradeRepository.existsById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void deleteTrade(Trade trade) {
        tradeRepository.delete(trade);
    }


}
