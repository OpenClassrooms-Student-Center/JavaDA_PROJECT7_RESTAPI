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

    /**
     * @param bidListRepository
     */
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
     *
     * @return
     */
    @Override
    public BidList save(BidList bid) {
        logger.debug("save bidList:{}", bid.getBid());

        bidListRepository.save(bid);

        return bid;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public BidList update(BidList bid) throws DataNotFoundException {
        logger.debug("update bid:{}", bid.getBid());
        BidList uploadBid = bidListRepository.findById(bid.getBidListId()).orElseThrow(() -> {
            logger.error("This bidId:{} not found!", bid.getBidListId());
            throw new DataNotFoundException("This bid doesn't exist with this id : " + bid.getBidListId() + " , from getBidById, BidListService.");
        });
        logger.info("Bid successfully found by its id(from getBidById,BidListService).");

        uploadBid.setAccount(bid.getAccount());
        uploadBid.setType(bid.getType());
        uploadBid.setBidQuantity(bid.getBidQuantity());

        return bidListRepository.save(uploadBid);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer bid) throws DataNotFoundException {
        logger.debug("delete bid:{}", bid);
        BidList deleteBid = bidListRepository.findById(bid).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + bid + " Not Present in Data Base");
        });

        bidListRepository.deleteById(deleteBid.getBidListId());
    }


}
