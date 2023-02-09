package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;

import java.util.List;

public interface BidListService {

    List<BidList> findAllBids();

    BidList findBidById(Integer id);

    BidList addBid(BidListDto bidList);

    BidList updateBid(Integer id, BidListDto bidListDto);

    void deleteBid(Integer id);
}
