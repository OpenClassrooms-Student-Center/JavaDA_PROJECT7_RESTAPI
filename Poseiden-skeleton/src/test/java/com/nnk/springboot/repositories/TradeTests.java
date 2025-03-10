package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade();
		trade.setAccount("Trade Account");
		trade.setType("Type");

		// Save
		trade = tradeRepository.save(trade);
		Integer generatedId = trade.getTradeId();

		Assert.assertNotNull(generatedId);
		Assert.assertTrue(generatedId > 0);
		Assert.assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		Trade updatedTrade = tradeRepository.save(trade);
		Assert.assertEquals("Trade Account Update", updatedTrade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Optional<Trade> foundTrade = tradeRepository.findById(generatedId);
		Assert.assertTrue(foundTrade.isPresent());

		// Delete
		tradeRepository.delete(trade);
		Optional<Trade> deletedTrade = tradeRepository.findById(generatedId);
		Assert.assertFalse(deletedTrade.isPresent());
	}
}