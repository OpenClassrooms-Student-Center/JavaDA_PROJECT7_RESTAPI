package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    List<CurvePoint> findAll();

    CurvePoint save(CurvePoint curve);

    void delete(CurvePoint curve);

    Optional<CurvePoint> findById(Integer id);

    CurvePoint findByCurveId(Integer id);

    @Override
    boolean existsById(Integer integer);
}
