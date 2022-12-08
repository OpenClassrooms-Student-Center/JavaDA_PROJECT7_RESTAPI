package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

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
    public BidList saveBidList(BidList bidList) {
	return bidListRepository.save(bidList);
    }

    @Override
    @Transactional
    public void deleteBidListById(Integer id) {
	bidListRepository.deleteById(id);
    }

}
