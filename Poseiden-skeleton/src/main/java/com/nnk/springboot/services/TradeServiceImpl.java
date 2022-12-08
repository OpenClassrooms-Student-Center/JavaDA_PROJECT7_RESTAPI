package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/*
 * Service for handling User related operations
 */
@Service
public class TradeServiceImpl implements ITradeService {

    private TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
	this.tradeRepository = tradeRepository;
    }

    @Override
    @Transactional
    public Iterable<Trade> getTrades() {
	return tradeRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Trade> getTradeById(Integer id) {
	return tradeRepository.findById(id);
    }

    @Override
    @Transactional
    public Trade saveTrade(Trade trade) {
	// Assigning by default role "User" (id = 1)
	// Role defaultRole = roleService.getRoleById(1);
	// user.addRole(defaultRole);
	return tradeRepository.save(trade);
    }

    @Override
    @Transactional
    public void deleteTradeById(Integer id) {
	tradeRepository.deleteById(id);
    }

}
