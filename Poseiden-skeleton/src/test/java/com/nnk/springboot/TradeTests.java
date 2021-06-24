package com.nnk.springboot;

import com.nnk.springboot.interfaces.TradeService;
import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private TradeService tradeService;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}

	@Test
	public void deleteTradeTest() {
		Trade trade = new Trade("Trade Account", "Type");
		trade = tradeRepository.save(trade);
		Integer id = trade.getId();

		tradeService.deleteTrade(id);
		Optional<Trade> tradeId = tradeRepository.findById(id);

		Assert.assertFalse(tradeId.isPresent());
	}
	@Test
	public void updateTradeTest() {
		//given
		Trade trade = new Trade("Trade Account", "Type");
		trade = tradeRepository.save(trade);
		Integer id = trade.getId();

		Trade updateTrade = new Trade("Test", "Test");

		//when
		tradeService.updateTrade(id, updateTrade);

		//then
		Assert.assertEquals(trade.getAccount(),"Test", "Test");
		Assert.assertEquals(trade.getType(),"Test", "Test");
	}

	@Test
	public void validateTradeTest() {
		//given
		Trade trade = new Trade("Trade Account", "Type");
		//when
		tradeService.validateTrade(trade);

		//then
		Trade tradeIdByAccountAndType = tradeRepository.findIdByAccountAndType("Trade Account", "Type");
		Optional<Trade> tradeId = tradeRepository.findById(tradeIdByAccountAndType.getId());
		Assert.assertTrue(tradeId.isPresent());
	}
}
