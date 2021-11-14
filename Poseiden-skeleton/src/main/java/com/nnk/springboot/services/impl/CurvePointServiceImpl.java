package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CurvePointServiceImpl implements CurvePointService {
    CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> delete(Integer id) {
        curvePointRepository.deleteById(id);
        return curvePointRepository.findAll();
    }

    @Override
    public List<CurvePoint> saveOrUpdate(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    @Override
    public List<CurvePoint> findAll() {
       return curvePointRepository.findAll();
    }
}
