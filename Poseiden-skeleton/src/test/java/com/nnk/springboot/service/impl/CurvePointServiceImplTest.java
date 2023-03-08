package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CurvePointServiceImpl.class)
class CurvePointServiceImplTest {

    private static final CurvePoint curvePoint = new CurvePoint();
    @MockBean
    private CurvePointRepository curvePointRepository;
    @Autowired
    private CurvePointService curvePointService;

    @BeforeEach
    private void init() {
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(10.0);
    }


    @Test
    final void testFindById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        CurvePoint foundCurvePoint = curvePointService.findById(1);

        assertThat(foundCurvePoint).isEqualTo(curvePoint);

    }

    @Test
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> curvePointService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        curvePointService.delete(1);

        verify(curvePointRepository, times(1)).delete(any(CurvePoint.class));
    }

    @Test
    final void testFindAll() {
        List<CurvePoint> findAll = new ArrayList<>();
        findAll.add(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(findAll);
        List<CurvePoint> foundCurvePoint = curvePointService.findAll();

        assertThat(foundCurvePoint).isEqualTo(findAll);
    }

    @Test
    final void testCreateCurvePoint() {
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(1);
        curvePointDto.setTerm(2.0);
        curvePointDto.setValue(10.0);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(new CurvePoint());
        CurvePoint result = curvePointService.create(curvePointDto);

        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateCurvePoint() {
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(2);
        curvePointDto.setTerm(3.0);
        curvePointDto.setValue(10.0);
        CurvePoint CurvePoint = new CurvePoint(curvePointDto);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(CurvePointServiceImplTest.curvePoint));
        when(curvePointRepository.getById(1)).thenReturn(CurvePointServiceImplTest.curvePoint);
        when(curvePointRepository.save(CurvePoint)).thenReturn(CurvePoint);
        curvePointService.update(1, curvePointDto);

        assertThat(CurvePointServiceImplTest.curvePoint.getCurveId()).isEqualTo(2);
        assertThat(CurvePointServiceImplTest.curvePoint.getTerm()).isEqualTo(3.0);
        assertThat(CurvePointServiceImplTest.curvePoint.getValue()).isEqualTo(10.0);
    }

    @Test
    final void testUpdateCurvePointThrowEntityNotFoundException() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(2);
        curvePointDto.setTerm(3.0);
        curvePointDto.setValue(10.0);

        assertThrows(NotFoundException.class, () -> curvePointService.update(1, curvePointDto));
    }
}

