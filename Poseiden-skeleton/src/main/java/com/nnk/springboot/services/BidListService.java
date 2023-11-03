package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

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

}
