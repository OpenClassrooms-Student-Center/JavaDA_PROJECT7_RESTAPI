package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

/**
 * The interface Curve point service.
 */
public interface CurvePointService {
    /**
     * Delete list.
     *
     * @param id the id
     * @return the list
     */
    List<CurvePoint> delete(Integer id);

    /**
     * Save or update list.
     *
     * @param curvePoint the curve point
     * @return the list
     */
    List<CurvePoint> saveOrUpdate(CurvePoint curvePoint);

    /**
     * Find by id object.
     *
     * @param id the id
     * @return the object
     */
    CurvePoint findById(Integer id);

    /**
     * Find all object.
     *
     * @return the object
     */
    List<CurvePoint>  findAll();
}
