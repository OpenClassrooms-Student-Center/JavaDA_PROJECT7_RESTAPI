package com.nnk.springboot.service.impl;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.orElseThrow(() -> new NotFoundException("Trade not found with id " + id));
    }

    @Override
    public Trade create(TradeDto tradeDto) {
        Trade trade = new Trade(tradeDto);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, TradeDto tradeDto) {
        Trade trade = findById(id);
        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyQuantity(tradeDto.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    @Override
    public void delete(Integer id) {
        Trade trade = findById(id);
        tradeRepository.delete(trade);
    }
}
