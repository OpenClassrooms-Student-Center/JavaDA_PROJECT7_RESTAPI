package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ICurvePointService {

    /**
     * get all CurvePoint
     * @return
     */
    List<CurvePoint> findAll();

    /**
     * find CurvePoint by id
     *
     * @param id
     * @return
     */
    Optional<CurvePoint> findById(Integer id) throws DataNotFoundException;

    /**
     * save given rating
     * @param curvePoint
     */
    void save(CurvePoint curvePoint);

    /**
     * update given curvePoint
     *
     * @param curvePoint
     * @return
     */
    CurvePoint update(CurvePoint curvePoint) throws DataNotFoundException;




    /**
     * delete given curvePoint
     * @param curvePoint
     */
    void delete(Integer curvePoint);
}
