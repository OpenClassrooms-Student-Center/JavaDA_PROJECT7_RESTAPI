package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public List<CurvePoint> findAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void saveCurvePoint(CurvePoint curve) {
        curvePointRepository.save(curve);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<CurvePoint> findCurvePointById(Integer id){
        return curvePointRepository.findById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public CurvePoint findByCurvePointId(Integer id){
        return curvePointRepository.findByCurvePointId(id);
    }

    @Override
    public boolean checkIfIdExists(int id) {
        return curvePointRepository.checkIfIdExists(id);
    }


}
