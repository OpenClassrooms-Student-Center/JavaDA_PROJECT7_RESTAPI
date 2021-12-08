package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	public List<CurvePoint> findAll() {
		return curvePointRepository.findAll();
	}
	
	public CurvePoint createCurvePoint(CurvePoint curvePoint) {
		curvePoint.setCreationDate​(new Timestamp(System.currentTimeMillis()));
		return curvePointRepository.save(curvePoint);
	}
	
	public CurvePoint updateCurvePoint(CurvePoint curvePoint, int id) throws EntityNotFoundException {
		if (curvePointRepository.findById(id) == null) {
			throw new EntityNotFoundException("CurvePoint does not exists");
		}
		CurvePoint updatedCurvePoint = curvePointRepository.getById(id);
		updatedCurvePoint.setCurveId(curvePoint.getCurveId​());
		updatedCurvePoint.setTerm(curvePoint.getTerm​());
		updatedCurvePoint.setValue(curvePoint.getValue​());
		return curvePointRepository.save(updatedCurvePoint);
	}
	
	public CurvePoint findById(int id) {
		return curvePointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BidList does not exists"));
	}
	
	public void deleteById(int id) {
		curvePointRepository.deleteById(id);
	}

}
