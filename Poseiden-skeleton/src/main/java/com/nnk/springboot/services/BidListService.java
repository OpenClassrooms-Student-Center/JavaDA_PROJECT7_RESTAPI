package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {


    /**
     * Return a list of all the Bids
     * @return List of BidList
     */
    List<BidList> findAllBid();

    /**
     * save the bid in db
     */
     void saveBid(BidList Bid);


    /**
     * find the bid in db
     * @param id
     * @return
     */
    Optional<BidList> findBidById(Integer id);

    /**
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * @param bid
     */
    void deleteBid(BidList bid);
}
