package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurvePointService.class)
public class CurvePointServiceTests extends TestVariables {

    @Autowired
    CurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        curvePoint.setId(1);
        when(curvePointRepository.findAll()).thenReturn(curvePointList);
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(curvePointOptional);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class FindAllTests {
        @Test
        public void findAllTest() {
            assertEquals(curvePointList, curvePointService.findAll());
            verify(curvePointRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class FindByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(curvePointOptional, curvePointService.findById(curvePoint.getId()));
            verify(curvePointRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), curvePointService.findById(curvePoint.getId()));
            verify(curvePointRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> curvePointService.findById(null));
            verify(curvePointRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class DeleteByIdTests {
        @Test
        public void deleteByIdTest() {
            curvePointService.deleteById(curvePoint.getId());
            verify(curvePointRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> curvePointService.deleteById(null));
            verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
