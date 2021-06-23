package com.nnk.springboot.repositories;

import com.nnk.springboot.model.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    CurvePoint findCurvePointById(int id);

    CurvePoint findCurvePointByCurveIdAndTermAndValue(Integer curveId, double term, double value );
}
