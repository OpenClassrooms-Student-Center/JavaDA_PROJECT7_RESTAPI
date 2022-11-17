package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;

public interface IBidListService {
    public Iterable<BidList> getBidLists();

    public Optional<BidList> getBidListById(Integer id);

    public BidList saveBidList(BidList bidList);

    public void deleteBidListById(Integer id);
}
