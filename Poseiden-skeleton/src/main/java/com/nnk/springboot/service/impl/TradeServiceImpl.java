package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.ITradeService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * contain all business service methods for TradeService
 */
@Service
public class TradeServiceImpl implements ITradeService {


    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade findById(Integer id) throws DataNotFoundException {
        return tradeRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Trade trade) {
        tradeRepository.delete(trade);

    }
}
