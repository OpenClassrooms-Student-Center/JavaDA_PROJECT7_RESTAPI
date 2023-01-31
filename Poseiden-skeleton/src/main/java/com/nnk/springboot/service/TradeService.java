package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> findAll(){
        return tradeRepository.findAll();
    }

    public Trade save(Trade trade){
        return tradeRepository.save(trade);
    }

    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
