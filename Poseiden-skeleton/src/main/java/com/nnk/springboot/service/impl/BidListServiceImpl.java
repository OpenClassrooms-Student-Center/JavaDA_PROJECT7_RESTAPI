package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.IBidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for BidListService
 */
@Service
public class BidListServiceImpl implements IBidListService {

    /**
     * SLF4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("BidListServiceImpl");

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
    public void update(BidList bidList) throws DataNotFoundException {
        logger.debug("update bidList:{}", bidList.getBid());
        Optional<BidList> isAlreadyBidList = bidListRepository.findById(bidList.getId().intValue());
        if (isAlreadyBidList.isPresent()) {
            bidListRepository.save(bidList);
        } else {
            throw new DataNotFoundException("No bidList " + bidList + " present in dataBase ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
