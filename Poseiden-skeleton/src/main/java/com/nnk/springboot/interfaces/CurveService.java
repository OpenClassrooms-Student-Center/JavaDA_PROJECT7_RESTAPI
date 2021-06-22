package com.nnk.springboot.interfaces;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddCurvePointForm;
import com.nnk.springboot.model.CurvePoint;

public interface CurveService {
    void validateCurvePoint(AddCurvePointForm addCurvePointForm) throws NumberFormatException ;
    void updateCurvePoint(Integer id, CurvePoint curvePoint) throws NumberFormatException;
    void deleteCurvePoint(Integer id);
}
