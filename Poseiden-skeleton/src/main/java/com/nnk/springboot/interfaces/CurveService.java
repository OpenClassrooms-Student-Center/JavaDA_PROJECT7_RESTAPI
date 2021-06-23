package com.nnk.springboot.interfaces;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.model.CurvePoint;

public interface CurveService {
    void validateCurvePoint(CurvePoint curvePoint);
    void updateCurvePoint(Integer id, CurvePoint curvePoint);
    void deleteCurvePoint(Integer id);
}
