package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}
	
	public Trade createTrade(Trade trade) {
		trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
		return tradeRepository.save(trade);
	}
	
	public Trade updateTrade(Trade trade, int id) throws EntityNotFoundException {
		if (tradeRepository.findById(id) == null) {
			throw new EntityNotFoundException("Trade does not exists");
		}
		Trade updatedTrade = tradeRepository.getById(id);
		updatedTrade.setAccount(trade.getAccount());
		updatedTrade.setType(trade.getType());
		updatedTrade.setBuyQuantity(trade.getBuyQuantity());
		updatedTrade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
		return tradeRepository.save(updatedTrade);
	}
	
	public Trade findById(int id) {
		return tradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trade does not exists"));
	}
	
	public void deleteById(int id) {
		tradeRepository.deleteById(id);
	}

}
