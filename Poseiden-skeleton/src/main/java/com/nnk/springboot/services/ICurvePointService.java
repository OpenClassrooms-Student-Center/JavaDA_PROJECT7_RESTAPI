package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;

public interface ICurvePointService {
    public Iterable<CurvePoint> getCurvePoints();

    public Optional<CurvePoint> getCurvePointById(Integer id);

    public CurvePoint saveCurvePoint(CurvePoint curvePoint);

    public void deleteCurvePointById(Integer id);
}
