package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import com.nnk.springboot.services.CurvePointService;

import java.util.List;
@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointService curvePointService;

	@Test
	@Transactional
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		assertNotNull(curvePoint.getCurveId());
		assertTrue(curvePoint.getCurveId() == 10);

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointService.findAllCurvePoint();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getCurveId();
		curvePointService.deleteCurvePoint(id);
		CurvePoint curvePointList;
		try {
			curvePointList = curvePointService.findCurvePointById(id);
		} catch (RequestedObjectNotFoundException e) {
			curvePointList=null;
		}
		assertNull(curvePointList);
	}

}
