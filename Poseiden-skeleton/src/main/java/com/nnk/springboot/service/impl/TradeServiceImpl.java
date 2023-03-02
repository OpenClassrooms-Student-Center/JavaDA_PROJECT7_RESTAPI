package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all Trade related business logic.
 */
@Service
@Slf4j
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    /**
     * Instantiates a new Trade service.
     *
     * @param tradeRepository the trade repository
     */
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        log.info("Retrieving all Tradess");
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        log.info("Retrieving Trades with id {}", id);
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.orElseThrow(() -> new NotFoundException("Trade not found with id " + id));
    }

    @Override
    public Trade create(TradeDto tradeDto) {
        log.info("Creating new Trades");
        Trade trade = new Trade(tradeDto);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, TradeDto tradeDto) {
        log.info("Updating Trades with id {}", id);
        Trade trade = findById(id);
        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyQuantity(tradeDto.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting Trades with id {}", id);
        Trade trade = findById(id);
        tradeRepository.delete(trade);
    }
}
