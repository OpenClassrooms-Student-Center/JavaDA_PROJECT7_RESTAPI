package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.BidList;

public interface IBidListService {
    public Iterable<BidList> getBidLists();

    public Optional<BidList> getBidListById(Integer id);

    public BidList saveBidList(BidList bidList);

    public void deleteBidListById(Integer id);
}
