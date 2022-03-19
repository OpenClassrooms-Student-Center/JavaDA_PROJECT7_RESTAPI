package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;
	
	public Iterable<CurvePoint> findAllCurvePoints() {
		log.info("All CurvePoint retrieved from database");
		return curvePointRepository.findAll();		
	}
	
	public Optional<CurvePoint> findCurvePointById(Integer id) {
		log.info("CurvePoint with id: " + id + " retrieved from database");
		return curvePointRepository.findById(id);
	}
	
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
		log.info("CurvePoint: " + curvePoint.toString() + " saved in database");
		return curvePointRepository.save(curvePoint);
	}
	
	public void deleteCurvePoint(CurvePoint curvePoint) {
		log.info("CurvePoint: " + curvePoint.toString() + " deleted in database");
		curvePointRepository.delete(curvePoint);
	} 
}
