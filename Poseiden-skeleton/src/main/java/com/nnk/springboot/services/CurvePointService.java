package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    private CurvePointRepository curvePointRepository;
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }
    public void saveCurve(CurvePoint curvePoint){
        curvePointRepository.save(curvePoint);
    }
    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }
    public CurvePoint findCurveById(Integer id){
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if(curvePoint.isPresent()){
            return curvePoint.get();
        }
        return null;
    }
    public void deleteCurve(CurvePoint curvePoint){
        curvePointRepository.delete(curvePoint);
    }
    public void deleteAllCurve(){curvePointRepository.deleteAll();}

}
