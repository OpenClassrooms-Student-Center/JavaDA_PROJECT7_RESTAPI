package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.IBidListService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * contain all business service methods for BidListService
 */
@Service
public class BidListServiceImpl implements IBidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BidList findById(Integer id) throws DataNotFoundException {
        return bidListRepository.findById(id).orElseThrow(() -> new DataNotFoundException(" No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(BidList bidList) {
        bidListRepository.save(bidList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
