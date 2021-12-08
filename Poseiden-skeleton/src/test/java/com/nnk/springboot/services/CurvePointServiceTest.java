package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(CurvePointService.class)
class CurvePointServiceTest {
	
	@MockBean
	private CurvePointRepository curvePointRepository;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private CurvePointService curvePointService;
	
	private static CurvePoint curvePoint = new CurvePoint();
	
	@BeforeEach
	private void init() {
		curvePoint.setId(1);
		curvePoint.setCurveId​(1);
		curvePoint.setTerm​(5.0);
		curvePoint.setValue​(2.0);
		
	}

	@Test
	final void testFindAll() {
		List<CurvePoint> findAll = new ArrayList<>();
		findAll.add(curvePoint);
		when(curvePointRepository.findAll()).thenReturn(findAll);
		List<CurvePoint> foundList = curvePointService.findAll();
		assertThat(foundList).isEqualTo(findAll);
	}

	@Test
	final void testCreateCurvePoint() {
		CurvePoint createCurvePoint = new CurvePoint();
		createCurvePoint.setId(2);
		createCurvePoint.setCurveId​(1);
		createCurvePoint.setTerm​(20.0);
		createCurvePoint.setValue​(10.0);
		when(curvePointRepository.save(createCurvePoint)).thenReturn(createCurvePoint);
		curvePointService.createCurvePoint(createCurvePoint);
		verify(curvePointRepository).save(createCurvePoint);
	}

	@Test
	final void testUpdateCurvePoint() {
		CurvePoint toUpdateCurvePoint = new CurvePoint();
		toUpdateCurvePoint.setId(1);
		toUpdateCurvePoint.setCurveId​(1);
		toUpdateCurvePoint.setTerm​(10.5);
		toUpdateCurvePoint.setValue​(5.0);
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		when(curvePointRepository.getById(1)).thenReturn(curvePoint);
		when(curvePointRepository.save(toUpdateCurvePoint)).thenReturn(toUpdateCurvePoint);
		curvePointService.updateCurvePoint(toUpdateCurvePoint, 1);
		assertThat(curvePoint.getCurveId​()).isEqualTo(1);
		assertThat(curvePoint.getTerm​()).isEqualTo(10.5);
		assertThat(curvePoint.getValue​()).isEqualTo(5.0);
	}
	
	@Test
	final void testUpdateBidListThrowEntityNotFoundException() {
		when(curvePointRepository.findById(1)).thenReturn(null);
		assertThrows(EntityNotFoundException.class, () -> curvePointService.updateCurvePoint(curvePoint, 1));
	}

	@Test
	final void testFindById() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		CurvePoint foudCurvePoint = curvePointService.findById(1);
		assertThat(foudCurvePoint).isEqualTo(curvePoint);
	}
	
	@Test
	final void testFindByIdNotFound() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, () -> curvePointService.findById(0));
	}

	@Test
	final void testDeleteById() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		curvePointService.deleteById(1);
		verify(curvePointRepository).deleteById(1);
	}

}
