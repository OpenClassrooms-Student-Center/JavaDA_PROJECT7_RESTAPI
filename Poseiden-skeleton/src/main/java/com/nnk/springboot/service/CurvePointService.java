package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;

import java.util.List;

public interface CurvePointService {
    List<CurvePoint> findAll();

    CurvePoint findById(Integer id);

    CurvePoint create(CurvePointDto curvePoint);

    CurvePoint update(Integer id, CurvePointDto curvePointDto);

    void delete(Integer id);
}
