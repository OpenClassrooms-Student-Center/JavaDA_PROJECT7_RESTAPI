package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    @Query(value = "", nativeQuery = true)
    CurvePoint update(CurvePoint curvePoint);
}
