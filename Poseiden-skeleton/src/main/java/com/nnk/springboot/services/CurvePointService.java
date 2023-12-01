package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {

    /**
     * Return a list of all the CurvePoints
     * @return List of BidList
     */
    List<CurvePoint> findAllCurvePoint();

    /**
     * save the curvePoint in db
     */
    void saveCurvePoint(CurvePoint curve);

    /**
     * find the curvePoint in db
     * @param id
     * @return
     */
    Optional<CurvePoint> findCurvePointById(Integer id);

    /**
     * @param id
     * @return
     */
    CurvePoint findByCurvePointId(Integer id);

    /**
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * @param curvePoint
     */
    void deleteCurvePoint(CurvePoint curvePoint);
}
