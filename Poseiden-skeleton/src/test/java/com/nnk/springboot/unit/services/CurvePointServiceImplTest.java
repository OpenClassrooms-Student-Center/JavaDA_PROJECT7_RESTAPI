package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurvePointServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class CurvePointServiceImplTest {

    CurvePointRepository curvePointRepository ;
    CurvePointServiceImpl service;

    @BeforeEach
    void init(){
        curvePointRepository = mock(CurvePointRepository.class);
        service = new CurvePointServiceImpl(curvePointRepository);
    }
    @Test
    public void shouldGetRepository() {
        Assert.assertEquals(ReflectionTestUtils.invokeMethod(service, "getRepository"), curvePointRepository);
    }
}
