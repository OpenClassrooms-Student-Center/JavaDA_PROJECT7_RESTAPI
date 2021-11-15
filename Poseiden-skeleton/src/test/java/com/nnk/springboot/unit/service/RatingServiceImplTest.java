package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.impl.RatingServiceAbstract;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    RatingRepository ratingRepository ;
    RatingServiceAbstract service;

    @BeforeEach
    void init(){
        ratingRepository = mock(RatingRepository.class);
        service = new RatingServiceAbstract(ratingRepository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), ratingRepository);
    }
}
