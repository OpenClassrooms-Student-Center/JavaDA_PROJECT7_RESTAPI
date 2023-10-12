package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingServiceTests extends TestVariables {

    RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Before
    public void setUpPerTest() {
        initializeVariables();
        ratingService = new RatingService(ratingRepository);
        when(ratingRepository.findAll()).thenReturn(ratingList);
        when(ratingRepository.findById(any(Integer.class))).thenReturn(ratingOptional);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ContextLoads() {}

    @Test
    public void findAllTest() {
        assertEquals(ratingList, ratingService.findAll());
        verify(ratingRepository, Mockito.times(1)).findAll();
    }
    
    @Test
    public void findByIdTest() {
        assertEquals(ratingOptional, ratingService.findById(1));
        verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
    }
    @Test
    public void findByIdTestIfNotFound() {
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), ratingService.findById(1));
        verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
    }
    @Test
    public void findByIdTestIfNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("id is null");
        ratingService.findById(null);
        verify(ratingRepository, Mockito.times(0)).findById(any(Integer.class));
    }

    @Test
    public void deleteByIdTest() {
        ratingService.deleteById(1);
        verify(ratingRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }
    @Test
    public void deleteByIdTestIfNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("id is null");
        ratingService.deleteById(null);
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}
