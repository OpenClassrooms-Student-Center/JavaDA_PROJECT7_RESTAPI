package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> implements  CurvePointService{
    CurvePointRepository repository;

    @Override
    protected JpaRepository<CurvePoint, Integer> getRepository() {
        return repository;
    }
}
