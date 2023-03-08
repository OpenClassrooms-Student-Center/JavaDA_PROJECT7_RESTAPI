package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(TradeServiceImpl.class)
class TradeServiceImplTest {

    private static final Trade trade = new Trade();
    @MockBean
    private TradeRepository tradeRepository;
    @Autowired
    private TradeService tradeService;

    @BeforeEach
    private void init() {
        trade.setTradeId(1);
        trade.setType("test");
        trade.setAccount("acc");
        trade.setBuyQuantity(12.0);
    }


    @Test
    final void testFindById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        Trade foundTrade = tradeService.findById(1);

        assertThat(foundTrade).isEqualTo(trade);

    }

    @Test
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> tradeService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        tradeService.delete(1);

        verify(tradeRepository, times(1)).delete(any(Trade.class));
    }

    @Test
    final void testFindAll() {
        List<Trade> findAll = new ArrayList<>();
        findAll.add(trade);

        when(tradeRepository.findAll()).thenReturn(findAll);
        List<Trade> foundTrade = tradeService.findAll();

        assertThat(foundTrade).isEqualTo(findAll);
    }

    @Test
    final void testCreateTrade() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setType("test");
        tradeDto.setAccount("acc");
        tradeDto.setBuyQuantity(12.0);

        when(tradeRepository.save(any(Trade.class))).thenReturn(new Trade());
        Trade result = tradeService.create(tradeDto);

        verify(tradeRepository, times(1)).save(any(Trade.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateTrade() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setType("test1");
        tradeDto.setAccount("acc2");
        tradeDto.setBuyQuantity(14.0);
        Trade Trade = new Trade(tradeDto);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(TradeServiceImplTest.trade));
        when(tradeRepository.getById(1)).thenReturn(TradeServiceImplTest.trade);
        when(tradeRepository.save(Trade)).thenReturn(Trade);
        tradeService.update(1, tradeDto);

        assertThat(TradeServiceImplTest.trade.getAccount()).isEqualTo("acc2");
        assertThat(TradeServiceImplTest.trade.getType()).isEqualTo("test1");
        assertThat(TradeServiceImplTest.trade.getBuyQuantity()).isEqualTo(14);
    }

    @Test
    final void testUpdateTradeThrowEntityNotFoundException() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());
        TradeDto tradeDto = new TradeDto();
        tradeDto.setType("test1");
        tradeDto.setAccount("acc2");
        tradeDto.setBuyQuantity(14.0);

        assertThrows(NotFoundException.class, () -> tradeService.update(1, tradeDto));
    }
}