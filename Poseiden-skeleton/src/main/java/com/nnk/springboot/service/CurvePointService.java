package com.nnk.springboot.service;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    @Autowired
    CurveController curveController;
    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }

    public CurvePoint save(CurvePoint curvePoint){
        return curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> findById(Integer id) {
        return curvePointRepository.findById(id);
    }

    public void delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
