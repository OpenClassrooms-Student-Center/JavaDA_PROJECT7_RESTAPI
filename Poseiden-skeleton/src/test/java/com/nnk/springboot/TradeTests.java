package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.TradeService;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeService tradeService;

	@Test
	@Transactional
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeService.save(trade);
		assertNotNull(trade.getTradeid());
		assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeService.save(trade);
		assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeService.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeid();
		tradeService.delete(id);
		Trade tradeList;
		try {
			tradeList =tradeService.findById(id);
		} catch (RequestedObjectNotFoundException e) {
			tradeList = null;
		}
		assertNull(tradeList);
	}
}
