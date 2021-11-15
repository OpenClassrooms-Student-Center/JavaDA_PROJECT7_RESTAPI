package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.services.impl.RuleServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;

public class RuleServiceImplTest {

    RuleRepository repository ;
    RuleServiceImpl service;

    @BeforeEach
    void init(){
        repository = mock(RuleRepository.class);
        service = new RuleServiceImpl(repository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), repository);
    }
}
