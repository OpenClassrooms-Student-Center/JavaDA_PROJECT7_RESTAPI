package com.nnk.springboot;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddBidListForm;
import com.nnk.springboot.forms.AddCurvePointForm;
import com.nnk.springboot.interfaces.CurveService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	private CurveService curveService;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertTrue(curvePoint.getCurveId() == 10);

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}

	@Test
	public void deleteCurvePointTest() {
		CurvePoint curvePoint = new CurvePoint(2,  3, 10);
		curvePoint = curvePointRepository.save(curvePoint);
		// Delete
		Integer id = curvePoint.getId();
		assertDoesNotThrow(() ->curveService.deleteCurvePoint(id));
		Optional<CurvePoint> curvePointId = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointId.isPresent());


	}

	@Test
	public void updateCurvePointTest() {
		//given
		CurvePoint curvePoint = new CurvePoint(2, 3, 10);
		curvePoint = curvePointRepository.save(curvePoint);
		Integer id = curvePoint.getId();

		CurvePoint updateCurve = new CurvePoint(3, 4, 20);

		//when
		assertDoesNotThrow(() ->curveService.updateCurvePoint(id, updateCurve ));

		//then
		Assert.assertEquals(curvePoint.getTerm(),4, 4);
	}

	@Test
	public void validateCurvePointTest() {
		//given
		AddCurvePointForm addCurvePointForm = new AddCurvePointForm("1", "2", "20");

		//when
		assertDoesNotThrow(() ->curveService.validateCurvePoint(addCurvePointForm));

		//then
		CurvePoint curvePoint = curvePointRepository.findCurvePointByCurveIdAndTermAndValue(1, 2, 20);
		Optional<CurvePoint> curve = curvePointRepository.findById(curvePoint.getId());
		Assert.assertTrue(curve.isPresent());
	}

	@Test
	public void validateCurvePointTest_Throw_AmountFormatException() {
		//given
		AddCurvePointForm curvePointForm = new AddCurvePointForm("Test", "Test", "aze");

		//when
		assertThrows(NumberFormatException.class, () ->curveService.validateCurvePoint(curvePointForm));
	}

	@Test
	public void updateCurvePointTest_Throw_AmountFormatException() {
		//given
		CurvePoint curvePoint = new CurvePoint(2, 3, 10);
		curvePoint = curvePointRepository.save(curvePoint);
		Integer id = curvePoint.getId();

		CurvePoint updateCurve = new CurvePoint();
		updateCurve.setCurveId(curvePoint.getCurveId());
		updateCurve.setTerm(curvePoint.getTerm());

		//when
		assertThrows(NumberFormatException.class, () ->curveService.updateCurvePoint(id, updateCurve));
	}

}
