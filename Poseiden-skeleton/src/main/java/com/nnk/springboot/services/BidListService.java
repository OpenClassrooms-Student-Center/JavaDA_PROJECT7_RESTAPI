package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class BidListService {
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository){
        this. bidListRepository= bidListRepository;
    }
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }
    public BidList save(BidList bid){
        return bidListRepository.save(bid);
    }
    public BidList getBidListById(Integer i){

        Optional<BidList> opt= bidListRepository.findById(i);
        return opt.get();

    }
}
