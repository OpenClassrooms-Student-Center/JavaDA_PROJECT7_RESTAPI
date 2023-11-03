package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public List<BidList> findAllBid() {
        return bidListRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void saveBid(BidList Bid) {
        bidListRepository.save(Bid);
    }
}
