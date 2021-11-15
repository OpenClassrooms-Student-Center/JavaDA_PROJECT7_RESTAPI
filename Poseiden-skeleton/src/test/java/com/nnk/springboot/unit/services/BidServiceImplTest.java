package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.services.impl.BidServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;

public class BidServiceImplTest {

    BidRepository repository ;
    BidServiceImpl service;

    @BeforeEach
    void init(){
        repository = mock(BidRepository.class);
        service = new BidServiceImpl(repository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), repository);
    }
}
