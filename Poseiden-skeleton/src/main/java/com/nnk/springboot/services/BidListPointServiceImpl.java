package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListPointServiceImpl implements IBidListService {
    
	private BidListRepository bidListRepository;

    public BidListPointServiceImpl(BidListRepository bidListRepository) {
    	this.bidListRepository = bidListRepository;
    }

    @Override
    @Transactional
    public Iterable<BidList> getBidLists() {
    	return bidListRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<BidList> getBidListById(Integer id) {
    	return bidListRepository.findById(id);
    }

    @Override
    @Transactional
    public BidList saveBidList(BidList curvePoint) {
	// Assigning by default role "User" (id = 1)
	//Role defaultRole = roleService.getRoleById(1);
	//user.addRole(defaultRole);
    	return bidListRepository.save(curvePoint);
    }

    @Override
    @Transactional
    public void deleteBidListById(Integer id) {
    	bidListRepository.deleteById(id);
    }

}
