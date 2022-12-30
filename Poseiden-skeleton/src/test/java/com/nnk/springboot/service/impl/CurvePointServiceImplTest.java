package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceImplTest {

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint1;

    private CurvePoint curvePoint2;



    @Test
    void findAllCurvePointTest() {
        //Given
        List<CurvePoint> allCurvePoints = List.of(
                new CurvePoint(1,1,1.0,1.0),
                new CurvePoint(2,1,2.0,2.0)
        );
        when(curvePointRepository.findAll()).thenReturn(allCurvePoints);

        //When
        List<CurvePoint> result = curvePointService.findAll();

        //Then
        Assert.assertEquals(result.get(1), allCurvePoints.get(1));




    }

    @Test
    void findByIdCurvePointTest() {

        //Given
        List<CurvePoint> allCurvePoints = List.of(
                new CurvePoint(1,1,1.0,1.0),
                new CurvePoint(2,1,2.0,2.0)
        );
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(allCurvePoints.get(1)));

        //When
        Optional<CurvePoint> result = curvePointService.findById(1);

        //Then
        Assert.assertEquals(result.get(), allCurvePoints.get(1));

    }

    @Test
    public void findByIdCurvePointTest_ShouldThrowException() throws DataNotFoundException {
        //Given
        when(curvePointRepository.findById(3)).thenReturn(Optional.empty());
        // When //Then
        assertThrows(DataNotFoundException.class, () -> curvePointService.findById(3));
    }

    @Test
    void saveCurvePointTest() {
        //Given
        CurvePoint curvePoint = new CurvePoint(1,1,1.0,1.0);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        //When
        CurvePoint result = curvePointService.save(curvePoint);

        //Then
        Assert.assertEquals(result, curvePoint);

    }

    @Test
    void updateCurvePointTest() {
        //Given
        CurvePoint curvePoint = new CurvePoint(1,1,1.0,1.0);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        //When
        CurvePoint result = curvePointService.update(curvePoint);

        //Then
        Assert.assertEquals(result, curvePoint);

    }

    @Test
    void deleteCurvePointTest() {

        //Given
        CurvePoint curvePoint = new CurvePoint(1,1,1.0,1.0);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        //When
        curvePointService.delete(1);

        //Then
        verify(curvePointRepository).findById(1);
    }

    @Test
    public void deleteCurvePointTest_ShouldThrowException() throws DataNotFoundException {
        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> curvePointService.delete(3));
    }

}