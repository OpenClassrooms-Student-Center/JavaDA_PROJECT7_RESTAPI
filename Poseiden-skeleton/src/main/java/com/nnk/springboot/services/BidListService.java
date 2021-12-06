package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import Exceptions.AlreadyExistException;



@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidListRepository;
	
	public List<BidList> findAll() {
		return bidListRepository.findAll();
	}
	
	public BidList createBidList(BidList bidList) throws AlreadyExistException {
		if (bidListRepository.findByAccount(bidList.getAccount()) != null) {
			throw new AlreadyExistException("This BidList already exists");
		}
		bidList.setCreationDate(new Timestamp(System.currentTimeMillis()));
		return bidListRepository.save(bidList);
	}
	
	public BidList updateBidList(BidList bidList, int id) throws EntityNotFoundException {
		if (bidListRepository.findById(id) == null) {
			throw new EntityNotFoundException("BidList does not exists");
		}
		BidList updatedBidList = bidListRepository.getById(id);
		updatedBidList.setAccount(bidList.getAccount());
		updatedBidList.setType(bidList.getType());
		updatedBidList.setBidQuantity(bidList.getBidQuantity());
		updatedBidList.setRevisionDate(new Timestamp(System.currentTimeMillis()));
		return bidListRepository.save(updatedBidList);
	}
	
	public BidList findById(int id) {
		return bidListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BidList does not exists"));
	}
	
	public void deleteById(int id) {
		bidListRepository.deleteById(id);
	}

}
