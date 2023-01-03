package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
public class CurvePointServiceTest {

    private ICurvePointService curvePointService;

    private static CurvePoint curvePointToAdd = new CurvePoint();

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	curvePointService = new CurvePointServiceImpl(curvePointRepository);
	curvePointToAdd.setCurvePointId(0);
    }

    @Test
    public void shouldGetAllCurvePoints() {
	List<CurvePoint> curvePointList = new ArrayList<>();
	curvePointList.add(curvePointToAdd);

	when(curvePointRepository.findAll()).thenReturn(curvePointList);

	List<CurvePoint> curvePoints = (List<CurvePoint>) curvePointService.getCurvePoints();
	assertEquals(curvePoints.get(0).getCurvePointId(), curvePointToAdd.getCurvePointId());
    }

    @Test
    public void shouldGetOneCurvePoint() {
	when(curvePointRepository.findById(curvePointToAdd.getCurvePointId())).thenReturn(Optional.of(curvePointToAdd));

	Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(curvePointToAdd.getCurvePointId());

	assertEquals(curvePoint.get().getCurvePointId(), curvePointToAdd.getCurvePointId());
    }

    @Test
    public void shouldSaveCurvePoint() {
	when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointToAdd);

	CurvePoint curvePointUser = curvePointService.saveCurvePoint(curvePointToAdd);

	assertEquals(curvePointUser.getCurvePointId(), curvePointToAdd.getCurvePointId());
    }

    @Test
    public void shouldDeleteCurvePoint() {
	curvePointService.deleteCurvePointById(curvePointToAdd.getCurvePointId());

	verify(curvePointRepository, times(1)).deleteById(curvePointToAdd.getCurvePointId());
    }

}
