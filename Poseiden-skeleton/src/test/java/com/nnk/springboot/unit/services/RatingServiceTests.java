package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RatingService.class)
public class RatingServiceTests extends TestVariables {

    @Autowired
    RatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    @Before
    public void setUpPerTest() {
        initializeVariables();
        when(ratingRepository.findAll()).thenReturn(ratingList);
    }

    @Test
    public void ContextLoads() {}
    @Test
    public void findAllTest() {
        assertEquals(ratingList, ratingService.findAll());
    }
}
