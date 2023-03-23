package com.poseidon.api.service;

import com.poseidon.api.model.CurvePoint;
import com.poseidon.api.model.dto.CurvePointDto;
import com.poseidon.api.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    ModelMapper modelMapper;


    public List<CurvePoint> findAllCurves() {
        return curvePointRepository.findAll();
    }

    public CurvePoint findCurvePointById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (id != null && curvePoint.isPresent()) {
            return curvePoint.get();
        }
        throw new UsernameNotFoundException("[SERVICE] Could not find Curve Point with id : " + id);
    }

    public boolean createCurve(CurvePoint curvePoint) {
        if (curvePoint != null
                && !curvePointRepository.findCurvePointById(curvePoint.getId()).isPresent()) {
            curvePointRepository.save(curvePoint);
            log.info("[SERVICE] Created new Curve Point with id : " + curvePoint.getCurveId() + " term : " + curvePoint.getTerm() + " and value : " + curvePoint.getValue());
            return true;
        }
        throw new UsernameNotFoundException("There was an error while creating your Curve Point");
    }

    public boolean updateCurve(Integer id, CurvePoint curvePointEntityUpdated) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findCurvePointById(id);
        if (id != null && curvePoint.isPresent()) {
            curvePointEntityUpdated.setId(id);
            curvePointRepository.save(curvePointEntityUpdated);

            log.info("[SERVICE] Updated Curve Point " + curvePointEntityUpdated.getCurveId() + " with term " + curvePointEntityUpdated.getTerm() + " and value " + curvePointEntityUpdated.getValue());
            return true;
        }
        throw new UsernameNotFoundException("Could not find Curve point with id : " + id);
    }

    public boolean deleteCurve(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findCurvePointById(id);
        if (id != null && curvePoint.isPresent()) {
            curvePointRepository.delete(curvePoint.get());
            log.info("[SERVICE] Deleted Curve Point with id : " + id );
            return true;
        }

        throw new UsernameNotFoundException("Could not find Curve Point with id : " + id);
    }

    public CurvePoint convertDtoToEntity(CurvePointDto curvePointDto) {
        return modelMapper.map(curvePointDto, CurvePoint.class);
    }

    public CurvePointDto convertEntityToDto(CurvePoint curvePointEntity) {
        return modelMapper.map(curvePointEntity, CurvePointDto.class);
    }
}
