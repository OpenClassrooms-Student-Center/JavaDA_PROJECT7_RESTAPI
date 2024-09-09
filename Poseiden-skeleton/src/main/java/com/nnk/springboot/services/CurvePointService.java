package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service layer for CurvePoint entity.
 *
 */
@Service
@RequiredArgsConstructor
public class CurvePointService {
    
    /**
     * The repository for CurvePoint entity.
     */
    private final CurvePointRepository curvePointRepository;

    /**
     * Save a CurvePoint into the repository.
     * @param curvePoint The CurvePoint to save.
     * @return The saved CurvePoint.
     */
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Find all CurvePoint in the repository.
     * @return A list of CurvePoint.
     */
    public List<CurvePoint> findAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     * Find a CurvePoint by its id.
     * @param id The id of the CurvePoint.
     * @return The CurvePoint with the given id.
     * @throws RequestedObjectNotFoundException If the CurvePoint is not found.
     */
    public CurvePoint findCurvePointById(Integer id) throws RequestedObjectNotFoundException {
        return curvePointRepository.findByCurvepointid(id).orElseThrow(()-> new RequestedObjectNotFoundException("CurvePoint not found with id: " + id));
    }
    
    /**
     * Delete a CurvePoint from the repository.
     * @param curvePoint The id of the CurvePoint to delete.
     */
    @Transactional
    public void deleteCurvePoint(int curvePoint) {
        curvePointRepository.deleteById(curvePoint);
    }
}

