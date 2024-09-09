package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass = CurvePoint.class, idClass = Integer.class)
public interface CurvePointRepository {
CurvePoint save(CurvePoint curvePoint);

List<CurvePoint> findAll();

Optional<CurvePoint> findByCurvepointid(Integer id);

void deleteById(int curvePoint);
}
