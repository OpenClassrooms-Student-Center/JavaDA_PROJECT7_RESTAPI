package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RatingService.class)
public class RatingServiceTests extends TestVariables {

    @Autowired
    RatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        rating.setId(1);
        when(ratingRepository.findAll()).thenReturn(ratingList);
        when(ratingRepository.findById(any(Integer.class))).thenReturn(ratingOptional);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class findAllTests {
        @Test
        public void findAllTest() {
            assertEquals(ratingList, ratingService.findAll());
            verify(ratingRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class findByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(ratingOptional, ratingService.findById(rating.getId()));
            verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), ratingService.findById(rating.getId()));
            verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> ratingService.findById(null));
            verify(ratingRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class deleteByIdTests {
        @Test
        public void deleteByIdTest() {
            ratingService.deleteById(rating.getId());
            verify(ratingRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> ratingService.deleteById(null));
            verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
