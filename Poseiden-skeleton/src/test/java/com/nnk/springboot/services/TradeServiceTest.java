package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(TradeService.class)
class TradeServiceTest {
	
	@MockBean
	private TradeRepository tradeRepository;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private TradeService tradeService;
	
	private static Trade trade = new Trade();
	
	@BeforeEach
	private void init() {
		trade.setTradeId(1);
		trade.setAccount("Test");
		trade.setType("Test");
        trade.setBuyQuantity(10.0);
	}

	@Test
	final void testFindAll() {
		List<Trade> findAll = new ArrayList<>();
		findAll.add(trade);
		when(tradeRepository.findAll()).thenReturn(findAll);
		List<Trade> foundList = tradeService.findAll();
		assertThat(foundList).isEqualTo(findAll);
	}

	@Test
	final void testCreateTrade() {
		Trade trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("Test");
		trade.setType("Test");
	    trade.setBuyQuantity(10.0);
	    when(tradeRepository.save(trade)).thenReturn(trade);
		tradeService.createTrade(trade);
		verify(tradeRepository).save(trade);
	}

	@Test
	final void testUpdateTrade() {
		Trade toUpdateTrade = new Trade();
		toUpdateTrade.setAccount("Account");
		toUpdateTrade.setType("Type");
	    toUpdateTrade.setBuyQuantity(5.0);
	    when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		when(tradeRepository.getById(1)).thenReturn(trade);
		when(tradeRepository.save(toUpdateTrade)).thenReturn(toUpdateTrade);
		tradeService.updateTrade(toUpdateTrade, 1);
		assertThat(trade.getAccount()).isEqualTo("Account");
		assertThat(trade.getType()).isEqualTo("Type");
		assertThat(trade.getBuyQuantity()).isEqualTo(5.0);
	}
	
	@Test
	final void testUpdateTradeThrowEntityNotFoundException() {
		when(tradeRepository.findById(1)).thenReturn(null);
		assertThrows(EntityNotFoundException.class, () -> tradeService.updateTrade(trade, 1));
	}

	@Test
	final void testFindById() {
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		Trade foundTrade = tradeService.findById(1);
		assertThat(foundTrade).isEqualTo(trade);
	}
	
	@Test
	final void testFindByIdNotFound() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, () -> tradeService.findById(0));
	}

	@Test
	final void testDeleteById() {
		tradeService.deleteById(1);
		verify(tradeRepository).deleteById(1);
	}

}
