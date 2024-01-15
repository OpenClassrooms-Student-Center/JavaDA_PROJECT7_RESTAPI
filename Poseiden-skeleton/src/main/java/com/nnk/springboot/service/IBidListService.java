package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

public interface IBidListService {
    BidList findById(Integer bidlistId);
    BidList saveBidlist(BidList bidList);
    BidList updateBidList(BidList bidList);
    boolean deleteBidList(BidList bidList);
}
