package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CurvePointService implements ICurvePointService {
    private final CurvePointRepository curvePointRepository;

    @Override
    public CurvePoint findById(Integer id) {
        Optional<CurvePoint> optionalCurvePoint =  curvePointRepository.findById(id);
        if(optionalCurvePoint.isPresent()) {
            return optionalCurvePoint.get();
        }
        return null;
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {

        return curvePointRepository.;
    }

    @Override
    public boolean deleteCurvePoint(CurvePoint curvePoint) {
       curvePointRepository.delete(curvePoint);
       return findById(curvePoint.getId()) == null;
    }
}
