package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.impl.TradeServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;

public class TradeServiceImplTest {

    TradeRepository repository ;
    TradeServiceImpl service;

    @BeforeEach
    void init(){
        repository = mock(TradeRepository.class);
        service = new TradeServiceImpl(repository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), repository);
    }
}
