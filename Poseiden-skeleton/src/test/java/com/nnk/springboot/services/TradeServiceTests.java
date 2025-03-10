package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTests {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
    }

    @Test
    void testGetAllTrades() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade));

        List<Trade> result = tradeService.getAllTrades();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Trade Account", result.get(0).getAccount());
    }

    @Test
    void testGetTradeById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Optional<Trade> result = tradeService.getTradeById(1);
        assertTrue(result.isPresent());
        assertEquals("Trade Account", result.get().getAccount());
    }

    @Test
    void testSaveTrade() {
        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade savedTrade = tradeService.saveTrade(trade);
        assertNotNull(savedTrade);
        assertEquals("Type", savedTrade.getType());
    }

    @Test
    void testDeleteTradeById() {
        doNothing().when(tradeRepository).deleteById(1);
        tradeService.deleteTradeById(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }
}
