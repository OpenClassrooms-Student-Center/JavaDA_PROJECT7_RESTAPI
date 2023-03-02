package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all CurvePoint related business logic.
 */
@Service
@Slf4j
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Instantiates a new Curve point service.
     *
     * @param curvePointRepository the curve point repository
     */
    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAll() {
        log.info("Retrieving all CurvePoint");
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        log.info("Retrieving CurvePoint with id {}", id);
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        return curvePoint.orElseThrow(() -> new NotFoundException("CurvePoint not found with id " + id));
    }

    @Override
    public CurvePoint create(CurvePointDto curvePointDto) {
        log.info("Creating new CurvePoint");
        CurvePoint curvePoint = new CurvePoint(curvePointDto);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint update(Integer id, CurvePointDto curvePointDto) {
        log.info("Updating CurvePoint with id {}", id);
        CurvePoint curvePoint = findById(id);
        curvePoint.setCurveId(curvePointDto.getCurveId());
        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePointDto.getValue());
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting CurvePoint with id {}", id);
        CurvePoint curvePoint = findById(id);
        curvePointRepository.delete(curvePoint);
    }
}
