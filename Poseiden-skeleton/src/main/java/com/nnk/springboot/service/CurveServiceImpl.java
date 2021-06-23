package com.nnk.springboot.service;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.interfaces.CurveService;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurveServiceImpl implements CurveService {
    
    private final CurvePointRepository curvePointRepository;


    
    @Override
    public void validateCurvePoint(CurvePoint curvePoint){

        CurvePoint addCurvePoint = new CurvePoint();
        addCurvePoint.setCurveId(curvePoint.getCurveId());
        addCurvePoint.setTerm(curvePoint.getTerm());
        addCurvePoint.setValue(curvePoint.getValue());
        curvePointRepository.save(addCurvePoint);
    }

    @Override
    public void updateCurvePoint(Integer id, CurvePoint curvePoint) {

        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findCurvePointById(id);
        curvePointRepository.delete(curvePoint);
    }
}
