package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class CurvePointService{
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    CurvePointRepository curvePointRepository;
    public CurvePointService(CurvePointRepository curvePointRepository){
        this.curvePointRepository=curvePointRepository;
    }
    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }
    public CurvePoint getCurvePointById(Integer i) throws Exception{
        Optional<CurvePoint> opt = curvePointRepository.findById(i);
        return opt.get();
    }
    //CREATE NEW CURVEPOINT
        public CurvePoint validateNewCurvePoint(CurvePoint curvePoint) throws Exception{
            return curvePointRepository.save(curvePoint);
        }
        //UPDATE CURVEPOINT

        public CurvePoint  updateCurvePoint(Integer id, CurvePoint updatedCurvePointEntity) throws Exception{
        Optional<CurvePoint> opt = curvePointRepository.findById(id);
        CurvePoint formerCurvePoint = opt.get();
        formerCurvePoint.setCurve_point_id(updatedCurvePointEntity.getCurve_point_id());
        formerCurvePoint.setTerm(updatedCurvePointEntity.getTerm());
        formerCurvePoint.setValue(updatedCurvePointEntity.getValue());
        return curvePointRepository.save(formerCurvePoint);
        }
        //DELETE CURVEPOINT
        public void deleteCurvePoint(Integer id) throws Exception{
            Optional<CurvePoint> opt = curvePointRepository.findById(id);
            CurvePoint curvePointToDelete = opt.get();
            curvePointRepository.delete(curvePointToDelete);

        }



}