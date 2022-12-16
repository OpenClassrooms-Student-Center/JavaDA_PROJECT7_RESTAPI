package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;

public interface ICurvePointService {

    /**
     * get all CurvePoint
     * @return
     */
    List<CurvePoint> findAll();

    /**
     * find CurvePoint by id
     * @param id
     * @return
     */
    CurvePoint findById(Integer id) throws DataNotFoundException;

    /**
     * save given rating
     * @param curvePoint
     */
    void save(CurvePoint curvePoint);

    /**
     * update given curvePoint
     * @param curvePoint
     */
    void update(CurvePoint curvePoint) throws DataNotFoundException;

    /**
     * delete given curvePoint
     * @param curvePoint
     */
    void delete(CurvePoint curvePoint);



}
