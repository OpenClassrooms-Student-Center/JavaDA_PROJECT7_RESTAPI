package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

    @Autowired
    private CurvePointService curvePointService;

    @Test
    public void curvePointTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);

        // Save
        curvePoint = curvePointService.save(curvePoint);
        Assert.assertNotNull(curvePoint.getId());
        Assert.assertTrue(curvePoint.getCurveId() == 10);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointService.save(curvePoint);
        Assert.assertTrue(curvePoint.getCurveId() == 20);

        // Find
        List<CurvePoint> listResult = curvePointService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointService.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointService.findById(id);
        Assert.assertFalse(curvePointList.isPresent());
    }

}

