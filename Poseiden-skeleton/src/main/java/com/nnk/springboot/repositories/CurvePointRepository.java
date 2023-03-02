package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for {@link CurvePoint} entities.
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
