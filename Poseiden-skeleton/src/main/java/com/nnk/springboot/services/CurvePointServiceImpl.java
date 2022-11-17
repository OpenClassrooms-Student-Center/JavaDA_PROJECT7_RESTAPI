package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;

/*
 * Service for handling User related operations
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {
    
	private CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
    	this.curvePointRepository = curvePointRepository;
    }

    @Override
    @Transactional
    public Iterable<CurvePoint> getCurvePoints() {
    	return curvePointRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<CurvePoint> getCurvePointById(Integer id) {
    	return curvePointRepository.findById(id);
    }

    @Override
    @Transactional
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
	// Assigning by default role "User" (id = 1)
	//Role defaultRole = roleService.getRoleById(1);
	//user.addRole(defaultRole);
	return curvePointRepository.save(curvePoint);
    }

    @Override
    @Transactional
    public void deleteCurvePointById(Integer id) {
    	curvePointRepository.deleteById(id);
    }

}
