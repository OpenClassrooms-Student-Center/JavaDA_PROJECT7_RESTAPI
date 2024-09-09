package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidListService {
    private final BidListRepository bidListRepository;
    
    /**
     * This method is used to get all BidList from database
     * @return List<BidList> all BidList in database
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }
    
    /**
     * This method is used to add a new BidList to database
     * @param bidList bidList to add
     * @return BidList save in database
     */
    public BidList addBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }
    
    /**
     * This method is used to get a BidList by id
     * @param id id of BidList
     * @return BidList get from database
     */
    public BidList getBidList(int id){
        return bidListRepository.findById(id).get();
    }
    
    /**
     * This method is used to find a BidList by id
     * @param id id of BidList
     * @return BidList get from database
     * @throws RequestedObjectNotFoundException if BidList not found
     */
    public BidList findById(int id) throws RequestedObjectNotFoundException{
        return bidListRepository.findById(id).orElseThrow(()-> new RequestedObjectNotFoundException("BidList not found with id: " + id));
    }
    
    /**
     * This method is used to delete a BidList by id
     * @param id id of BidList
     */
    @Transactional
    public void deleteBidList(int id) {
        bidListRepository.deleteByBidlistid(id);
    }
}

