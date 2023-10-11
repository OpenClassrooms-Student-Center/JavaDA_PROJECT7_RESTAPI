package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
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
    }

    @Test
    public void ContextLoads() {}
    
    @Test
    public void findAllTest() {
        assertEquals(ratingList, ratingService.findAll());
    }
}
