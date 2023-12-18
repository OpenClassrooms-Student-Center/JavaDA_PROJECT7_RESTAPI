package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return curvePointRepository.findById(id);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid curvePoint Id:" + id)));
        curvePointRepository.deleteById(id);
    }

    public void save(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }
}
