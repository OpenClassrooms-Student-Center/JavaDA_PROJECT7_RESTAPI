package com.poseidon.api.repositories;

import com.poseidon.api.model.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Long> {

    Optional<CurvePoint> findCurvePointById(Long id);
}
