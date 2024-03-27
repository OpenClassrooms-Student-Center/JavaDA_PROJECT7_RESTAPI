package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {
    private BidListRepository bidListRepository;
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public void saveBidList(BidList bidList){
        bidListRepository.save(bidList);
    }
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }
    public BidList findBidListById(Integer id){
        Optional<BidList> bidList = bidListRepository.findById(id);
        if(bidList.isPresent()){
            return bidList.get();
        }
        return null;
    }
    public void deleteBidList(BidList bidList){
        bidListRepository.delete(bidList);
    }
    public void deleteAllBidList(){
        bidListRepository.deleteAll();
    }
}
