package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.ICurvePointService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * contain all business service methods for curvePoint
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint findById(Integer id) throws DataNotFoundException {
        return curvePointRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CurvePoint curvePoint) {
    curvePointRepository.delete(curvePoint);
    }
}
