package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
    public class BidListService {
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository){
        this. bidListRepository= bidListRepository;
    }
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }
}
