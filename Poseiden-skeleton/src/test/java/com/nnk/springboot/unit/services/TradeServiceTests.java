package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TradeService.class)
public class TradeServiceTests extends TestVariables {

    @Autowired
    TradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        trade.setId(1);
        when(tradeRepository.findAll()).thenReturn(tradeList);
        when(tradeRepository.findById(any(Integer.class))).thenReturn(tradeOptional);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class FindAllTests {
        @Test
        public void findAllTest() {
            assertEquals(tradeList, tradeService.findAll());
            verify(tradeRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class FindByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(tradeOptional, tradeService.findById(trade.getId()));
            verify(tradeRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), tradeService.findById(trade.getId()));
            verify(tradeRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> tradeService.findById(null));
            verify(tradeRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class DeleteByIdTests {
        @Test
        public void deleteByIdTest() {
            tradeService.deleteById(trade.getId());
            verify(tradeRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> tradeService.deleteById(null));
            verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
