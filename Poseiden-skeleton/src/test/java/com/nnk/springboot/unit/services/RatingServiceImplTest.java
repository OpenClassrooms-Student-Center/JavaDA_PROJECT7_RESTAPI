package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.impl.RatingServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    RatingRepository ratingRepository ;
    RatingServiceImpl service;

    @BeforeEach
    void init(){
        ratingRepository = mock(RatingRepository.class);
        service = new RatingServiceImpl(ratingRepository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), ratingRepository);
    }
}
