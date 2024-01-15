package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

public interface ICurvePointService {
    CurvePoint findById(Integer id);
    CurvePoint saveCurvePoint(CurvePoint curvePoint);
    CurvePoint updateCurvePoint(CurvePoint curvePoint);
    boolean deleteCurvePoint(CurvePoint curvePoint);
}
