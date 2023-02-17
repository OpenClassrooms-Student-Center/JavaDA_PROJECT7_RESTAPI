package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        return curvePoint.orElseThrow(() -> new NotFoundException("CurvePoint not found with id " + id));
    }

    @Override
    public CurvePoint create(CurvePointDto curvePointDto) {
        CurvePoint curvePoint = new CurvePoint(curvePointDto);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint update(Integer id, CurvePointDto curvePointDto) {
        CurvePoint curvePoint = findById(id);
        curvePoint.setCurveId(curvePointDto.getCurveId());
        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePointDto.getValue());
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void delete(Integer id) {
        CurvePoint curvePoint = findById(id);
        curvePointRepository.delete(curvePoint);
    }
}
