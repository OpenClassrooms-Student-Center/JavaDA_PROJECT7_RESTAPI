package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeRepository tradeRepository;


    private Trade trade1;
    private Trade trade2;



    @BeforeEach
    void setUp() {
        trade1 = new Trade();
        trade2 = new Trade();


    }


    @Test
    void findAllTrade() {
        //Given
        List<Trade> allTrades = List.of(
                new Trade("NewTrade1", "Type1", 1D),
                new Trade("NewTrade2", "Type2", 2D)
        );

        when(tradeRepository.findAll()).thenReturn(allTrades);


        //When
        List<Trade> tradeResult = tradeService.findAll();

        //Then
        Assertions.assertEquals(tradeResult.size(), 2);

    }

    @Test
    void findByIdTradeTest() {
        //Given
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("NewTrade");
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        //When
        Optional<Trade> tradeResult = tradeService.findById(1);

        //Then
        Assertions.assertEquals(tradeResult.get().getAccount(), "NewTrade");
    }

    @Test
    public void findByIdTradeTest_ShouldThrowException() throws DataNotFoundException {
        //Given
        when(tradeRepository.findById(3)).thenReturn(Optional.empty());
        // When //Then
        assertThrows(DataNotFoundException.class, () -> tradeService.findById(3));
    }

    @Test
    void saveTradeTest() {
        //Given
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("NewAccount1");
        when(tradeRepository.save(any(Trade.class))).thenReturn((trade));

        //When
        Trade tradeResult = tradeService.save(trade);

        //Then
        Assertions.assertEquals(tradeResult.getAccount(), "NewAccount1");

    }

    @Test
    void updateTradeTest() {

        //Given
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("NewAccount1");
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        //When
        Trade tradeResult = tradeService.update(trade);

        //Then
        Assertions.assertEquals(tradeResult.getAccount(), "NewAccount1");


    }

    @Test
    public void updateTradeNameTest_ShouldThrowException() throws DataNotFoundException {

        //Given  // When //Then
        assertThrows(DataNotFoundException.class, () -> tradeService.update(trade1));
    }

    @Test
    void deleteTradeTest() {

        //Given
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("NewAccount1");
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        // ACT
        tradeService.delete(1);
        //ASSERT
        verify(tradeRepository).findById(1);
    }

    @Test
    public void deleteTradeListTest_ShouldThrowException() throws DataNotFoundException {

        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> tradeService.delete(trade1.getTradeId()));
    }



}