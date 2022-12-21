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
    public Optional<BidList> findById(Integer id) throws DataNotFoundException {
        logger.debug("find bidById:{}", id);
        return Optional.ofNullable(bidListRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Invalid bid Id: {} ", id);
            return new DataNotFoundException("Invalid bid Id:" + id);
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(BidList bid) {
        logger.debug("save bidList:{}", bid.getBid());
        bidListRepository.save(bid);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public BidList update(BidList bid) {
        logger.debug("update bid:{}", bid.getBid());
        return bidListRepository.save(bid);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(BidList bid) {
        logger.debug("delete bid:{}", bid.getBid());
        bidListRepository.delete(bid);
    }


}
