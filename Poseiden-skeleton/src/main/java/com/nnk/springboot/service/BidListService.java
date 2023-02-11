package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;

import java.util.List;

public interface BidListService {

    List<BidList> findAll();

    BidList findById(Integer id);

    BidList create(BidListDto bidList);

    BidList update(Integer id, BidListDto bidListDto);

    void delete(Integer id);
}
