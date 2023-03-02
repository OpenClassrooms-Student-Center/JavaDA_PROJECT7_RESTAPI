package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;

import java.util.List;

/**
 * The interface Curve point service.
 */
public interface CurvePointService {
    /**
     * Find CurvePoint all list.
     *
     * @return the list
     */
    List<CurvePoint> findAll();

    /**
     * Find CurvePoint by id.
     *
     * @param id the id
     * @return the curve point
     */
    CurvePoint findById(Integer id);

    /**
     * Create curve point.
     *
     * @param curvePoint the curve point
     * @return the curve point
     */
    CurvePoint create(CurvePointDto curvePoint);

    /**
     * Update curve point.
     *
     * @param id            the id
     * @param curvePointDto the curve point dto
     * @return the curve point
     */
    CurvePoint update(Integer id, CurvePointDto curvePointDto);

    /**
     * Delete CurvePoint by id.
     *
     * @param id the id
     */
    void delete(Integer id);
}
