package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;

public class UserServiceImplTest {

    UserRepository repository ;
    UserServiceImpl service;

    @BeforeEach
    void init(){
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);
    }

    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), repository);
    }
}
