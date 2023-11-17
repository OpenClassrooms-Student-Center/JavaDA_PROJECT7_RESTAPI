package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<BidList> findBidById(Integer id){
        return bidListRepository.findById(id);
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public boolean checkIfIdExists(int id) {
        return bidListRepository.existsById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void deleteBid(BidList bid){
        bidListRepository.delete(bid);
    }
}
