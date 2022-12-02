package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

/*
 * Service for handling User related operations
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    private CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
	this.curvePointRepository = curvePointRepository;
    }

    @Override
    @Transactional
    public Iterable<CurvePoint> getCurvePoints() {
	return curvePointRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<CurvePoint> getCurvePointById(Integer id) {
	return curvePointRepository.findById(id);
    }

    @Override
    @Transactional
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
	return curvePointRepository.save(curvePoint);
    }

    @Override
    @Transactional
    public void deleteCurvePointById(Integer id) {
	curvePointRepository.deleteById(id);
    }

}
