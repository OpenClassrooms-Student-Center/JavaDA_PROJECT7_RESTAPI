package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class CurvePointService{

    CurvePointRepository curvePointRepository;
    public CurvePointService(CurvePointRepository curvePointRepository){
        this.curvePointRepository=curvePointRepository;
    }
    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }
    public CurvePoint getCurvePointById(Integer i){
        Optional<CurvePoint> opt = curvePointRepository.findById(i);
        return opt.get();
    }
    //CREATE NEW CURVEPOINT
        public CurvePoint validateNewCurvePoint(CurvePoint curvePoint){
            return curvePointRepository.save(curvePoint);
        }
        //UPDATE CURVEPOINT

        public CurvePoint  updateCurvePoint(Integer id, CurvePoint updatedCurvePointEntity){
        Optional<CurvePoint> opt = curvePointRepository.findById(id);
        CurvePoint formerCurvePoint = opt.get();
        formerCurvePoint.setCurve_point_id(updatedCurvePointEntity.getCurve_point_id());
        formerCurvePoint.setTerm(updatedCurvePointEntity.getTerm());
        formerCurvePoint.setValue(updatedCurvePointEntity.getValue());
        return curvePointRepository.save(formerCurvePoint);
        }
        //DELETE CURVEPOINT
        public void deleteCurvePoint(Integer id){
            Optional<CurvePoint> opt = curvePointRepository.findById(id);
            CurvePoint curvePointToDelete = opt.get();
            curvePointRepository.delete(curvePointToDelete);

        }



}