package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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

@SpringBootTest(classes = BidListService.class)
public class BidListServiceTests extends TestVariables {

    @Autowired
    BidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        bidList.setId(1);
        when(bidListRepository.findAll()).thenReturn(bidListList);
        when(bidListRepository.findById(any(Integer.class))).thenReturn(bidListOptional);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class FindAllTests {
        @Test
        public void findAllTest() {
            assertEquals(bidListList, bidListService.findAll());
            verify(bidListRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class FindByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(bidListOptional, bidListService.findById(bidList.getId()));
            verify(bidListRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), bidListService.findById(bidList.getId()));
            verify(bidListRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> bidListService.findById(null));
            verify(bidListRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class DeleteByIdTests {
        @Test
        public void deleteByIdTest() {
            bidListService.deleteById(bidList.getId());
            verify(bidListRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> bidListService.deleteById(null));
            verify(bidListRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
