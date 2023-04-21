package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.Assert;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)

public class TradeServiceTests {

	@Mock
	private TradeRepository tradeRepository;

	TradeService tradeService;

	Trade trade = new Trade();

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.openMocks(this);
		trade.setTrade_id(1);
		trade.setAccount("account");
		trade.setType("type");

		tradeService = new TradeService(tradeRepository);
	}
	@Test
	public void findByIdTest() throws Exception {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

		assertEquals(trade,tradeService.getTradeById(1));
	}

	@Test
	public void findAllTest(){
		//ARRANGE
		List<Trade> listOfTrades = new ArrayList<>();
		when(tradeRepository.findAll()).thenReturn(listOfTrades);
		//ACT and ASSERT
		assertEquals(listOfTrades, tradeService.findAll());
	}
	@Test
	public void validateNewTradeTest() throws Exception {
		//ARRANGE
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
		//ACT
		Trade registeredTrade = tradeService.validateNewTrade(trade);
		//ASSERT
		assertNotNull(registeredTrade);
		assertEquals("account", registeredTrade.getAccount());
		assertEquals("type", registeredTrade.getType());

		verify(tradeRepository, times(1)).save(registeredTrade);
	}
	@Test
	public void updateBidListTest() throws Exception {
		//ARRANGE

		Trade updatedTradeEntity = new Trade("account", "type", 3.5);
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		when(tradeRepository.save(trade)).thenReturn(trade);
		//ACT
		Trade result = tradeService.updateTrade(trade.getTrade_id(), updatedTradeEntity);
		//ASSERT
		assertEquals(trade.getAccount(), result.getAccount());
		assertEquals(trade.getType(), result.getType());

		assertEquals(1, result.getTrade_id());

	}
	@Test
	public void deleteBidListTest() throws Exception {
		//ARRANGE
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		doNothing().when(tradeRepository).delete(trade);
		//ACT
		tradeService.deleteTrade(1);
		//ASSERT
		verify(tradeRepository, times(1)).delete(trade);
	}

	/*@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getTrade_id());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTrade_id();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}*/
}
