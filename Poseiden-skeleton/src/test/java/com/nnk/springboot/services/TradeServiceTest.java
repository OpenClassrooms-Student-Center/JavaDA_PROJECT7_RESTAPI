package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@SpringBootTest
public class TradeServiceTest {

    private ITradeService tradeService;

    private static Trade tradeToAdd = new Trade();

    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	tradeService = new TradeServiceImpl(tradeRepository);
	tradeToAdd.setTradeId(0);
    }

    @Test
    public void shouldGetAllTrades() {
	List<Trade> tradeList = new ArrayList<>();
	tradeList.add(tradeToAdd);

	when(tradeRepository.findAll()).thenReturn(tradeList);

	List<Trade> trades = (List<Trade>) tradeService.getTrades();
	assertEquals(trades.get(0).getTradeId(), tradeToAdd.getTradeId());
    }

    @Test
    public void shouldGetOneTrade() {
	when(tradeRepository.findById(tradeToAdd.getTradeId())).thenReturn(Optional.of(tradeToAdd));

	Optional<Trade> trade = tradeService.getTradeById(tradeToAdd.getTradeId());

	assertEquals(trade.get().getTradeId(), tradeToAdd.getTradeId());
    }

    @Test
    public void shouldSaveTrade() {
	when(tradeRepository.save(any(Trade.class))).thenReturn(tradeToAdd);

	Trade tradeUser = tradeService.saveTrade(tradeToAdd);

	assertEquals(tradeUser.getTradeId(), tradeToAdd.getTradeId());
    }

    @Test
    public void shouldDeleteTrade() {
	tradeService.deleteTradeById(tradeToAdd.getTradeId());

	verify(tradeRepository, times(1)).deleteById(tradeToAdd.getTradeId());
    }

}
