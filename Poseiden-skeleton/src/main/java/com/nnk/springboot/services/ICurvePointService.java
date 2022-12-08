package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.CurvePoint;

public interface ICurvePointService {
    public Iterable<CurvePoint> getCurvePoints();

    public Optional<CurvePoint> getCurvePointById(Integer id);

    public CurvePoint saveCurvePoint(CurvePoint curvePoint);

    public void deleteCurvePointById(Integer id);
}
