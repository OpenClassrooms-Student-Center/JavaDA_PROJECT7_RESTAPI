package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	public Iterable<Trade> findAllTrades() {
		log.info("All Trade retrieved from database");
		return tradeRepository.findAll();		
	}
	
	public Optional<Trade> findTradeById(Integer id) {
		log.info("Trade with id: " + id + " from database");
		return tradeRepository.findById(id);
	}
	
	public Trade saveTrade(Trade trade) {
		log.info("Trade: " + trade.toString() + " saved in database");
		return tradeRepository.save(trade);
	}
	
	public void deleteTrade(Trade trade) {
		log.info("Trade: " + trade.toString() + " deleted in database");
		tradeRepository.delete(trade);
	} 
}
