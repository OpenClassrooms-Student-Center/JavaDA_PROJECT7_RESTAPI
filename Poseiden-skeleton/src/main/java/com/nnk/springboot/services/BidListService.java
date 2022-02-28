package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BidListService {

	@Autowired
	private BidListRepository bidListRepository;
	
	public Iterable<BidList> findAllBidLists() {
		log.info("All BidLists retrieved from database");
		return bidListRepository.findAll();		
	}
	
	public Optional<BidList> findBidListById(Integer id) {
		log.info("BidList with id: " + id + " retrieved from database");
		return bidListRepository.findById(id);
	}
	
	public BidList saveBidList(BidList bidList) {
		log.info("BidList: " + bidList.toString() + " saved in database");
		return bidListRepository.save(bidList);
	}
	
	public void deleteBidList(BidList bidList) {
		log.info("BidList: " + bidList.toString() + " deleted in database");
		bidListRepository.delete(bidList);
	}
}
