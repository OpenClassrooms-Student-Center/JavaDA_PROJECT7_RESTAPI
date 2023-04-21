package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)

public class CurvePointServiceTests {

	@Mock
	private CurvePointRepository curvePointRepository;

	CurvePointService curvePointService;

	CurvePoint curvePoint = new CurvePoint();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		curvePoint.setCurve_id(1);
		curvePoint.setCurve_point_id(2);
		curvePoint.setTerm(2.5);
		curvePoint.setValue(3.5);

		curvePointService = new CurvePointService(curvePointRepository);
	}
	@Test
	public void findByIdTest() throws Exception {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

		assertEquals(curvePoint,curvePointService.getCurvePointById(1));
	}

	@Test
	public void findAllTest(){
		//ARRANGE
		List<CurvePoint> listOfCurvePoints = new ArrayList<>();
		when(curvePointRepository.findAll()).thenReturn(listOfCurvePoints);
		//ACT and ASSERT
		assertEquals(listOfCurvePoints, curvePointService.findAll());
	}
	@Test
	public void validateNewBidListTest() throws Exception {
		//ARRANGE
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
		//ACT
		CurvePoint registeredCurvePoint = curvePointService.validateNewCurvePoint(curvePoint);
		//ASSERT
		assertNotNull(registeredCurvePoint);
		assertEquals(2, registeredCurvePoint.getCurve_point_id());
		assertEquals(2.5, registeredCurvePoint.getTerm());
		assertEquals(3.5, registeredCurvePoint.getValue());

		verify(curvePointRepository, times(1)).save(registeredCurvePoint);
	}
	@Test
	public void updateBidListTest() throws Exception {
		//ARRANGE
		Integer id = 1;
		CurvePoint curvePointToUpdate = new CurvePoint();
		curvePointToUpdate.setCurve_id(1);
		CurvePoint updatedCurvePointEntity = new CurvePoint(5, 5.5, 15.5);
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePointToUpdate));
		when(curvePointRepository.save(curvePointToUpdate)).thenReturn(curvePointToUpdate);
		//ACT
		CurvePoint result = curvePointService.updateCurvePoint(id, updatedCurvePointEntity);
		//ASSERT
		assertEquals(curvePointToUpdate.getCurve_point_id(), result.getCurve_point_id() );
		assertEquals(curvePointToUpdate.getTerm(), result.getTerm());
		assertEquals(curvePointToUpdate.getValue(), result.getValue());


		assertEquals(1, result.getCurve_id());

	}
	@org.junit.jupiter.api.Test
	public void deleteBidListTest() throws Exception {
		//ARRANGE
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		doNothing().when(curvePointRepository).delete(curvePoint);
		//ACT
		curvePointService.deleteCurvePoint(1);
		//ASSERT
		verify(curvePointRepository, times(1)).delete(curvePoint);
	}

}
