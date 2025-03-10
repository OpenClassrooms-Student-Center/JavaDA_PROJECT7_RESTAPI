package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTests {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
    }

    @Test
    void testGetAllCurvePoint() {
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));

        List<CurvePoint> result = curvePointService.getAllCurvePoint();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getCurveId());
    }

    @Test
    void testGetCurvePointById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        Optional<CurvePoint> result = curvePointService.getCurvePointById(1);
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getCurveId());
    }

    @Test
    void testSaveCurvePoint() {
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint savedCurvePoint = curvePointService.saveCurvePoint(curvePoint);
        assertNotNull(savedCurvePoint);
        assertEquals(30d, savedCurvePoint.getValue());
    }

    @Test
    void testDeleteCurvePointById() {
        doNothing().when(curvePointRepository).deleteById(1);
        curvePointService.deleteCurvePointById(1);
        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
