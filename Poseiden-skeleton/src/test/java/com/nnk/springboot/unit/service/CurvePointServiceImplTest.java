package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurvePointServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

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
