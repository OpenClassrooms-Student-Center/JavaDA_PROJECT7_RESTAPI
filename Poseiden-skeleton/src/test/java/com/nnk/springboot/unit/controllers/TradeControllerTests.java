package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TradeController.class)
public class TradeControllerTests extends TestVariables {

    @Autowired
    TradeController tradeController;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        trade.setId(1);

        when(tradeService.findAll()).thenReturn(tradeList);
        when(tradeService.findById(any(Integer.class))).thenReturn(tradeOptional);
        when(tradeService.findById(any(Integer.class))).thenReturn(tradeOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        public void homeTest () {
            assertEquals("trade/list", tradeController.home(model));
            verify(tradeService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddTradeFormTests {
        @Test
        public void addTradeFormTest () {
            assertEquals("trade/add", tradeController.addTradeForm(trade));
        }
        @Test
        public void addTradeFormTestIfEmpty () {
            assertEquals("trade/add", tradeController.addTradeForm(new Trade()));
        }
        @Test
        public void addTradeFormTestIfNull () {
            assertEquals("trade/add", tradeController.addTradeForm(null));
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        public void validateTest () {
            assertEquals("redirect:/trade/list", tradeController.validate(trade, bindingResult, model));
            verify(tradeService, Mockito.times(1)).save(any(Trade.class));
        }
        @Test
        public void validateTestIfInvalidTrade () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("trade/add", tradeController.validate(trade, bindingResult, model));
            verify(tradeService, Mockito.times(0)).save(any(Trade.class));
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        public void showUpdateFormTest () {
            assertEquals("trade/update", tradeController.showUpdateForm(trade.getId(), model));
            verify(tradeService, Mockito.times(1)).findById(any(Integer.class));
        }
        @Test
        public void showUpdateFormTestIfTradeNotFound () {
            when(tradeService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> tradeController.showUpdateForm(trade.getId(), model));
            verify(tradeService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateTradeTests {
        @Test
        public void updateTradeTest () {
            assertEquals("redirect:/trade/list", tradeController.updateTrade(trade.getId(), trade, bindingResult, model));
            verify(tradeService, Mockito.times(1)).findById(any(Integer.class));
            verify(tradeService, Mockito.times(1)).save(any(Trade.class));
        }
        @Test
        public void updateTradeTestIfInvalidTrade () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/trade/update/{id}", tradeController.updateTrade(trade.getId(), trade, bindingResult, model));
            verify(tradeService, Mockito.times(0)).findById(any(Integer.class));
            verify(tradeService, Mockito.times(0)).save(any(Trade.class));
        }
        @Test
        public void updateTradeTestIfTradeNotInDB () {
            when(tradeService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> tradeController.updateTrade(trade.getId(), trade, bindingResult, model));
            verify(tradeService, Mockito.times(1)).findById(any(Integer.class));
            verify(tradeService, Mockito.times(0)).save(any(Trade.class));
        }
    }

    @Nested
    public class DeleteTradeTests {
        @Test
        public void deleteTradeTest () {
            assertEquals("redirect:/trade/list", tradeController.deleteTrade(trade.getId(), model));
            verify(tradeService, Mockito.times(1)).deleteById(any(Integer.class));
        }
    }
}
