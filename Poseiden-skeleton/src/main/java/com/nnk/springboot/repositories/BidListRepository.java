package com.nnk.springboot.repositories;

import com.nnk.springboot.model.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    @Query("select bidList from BidList bidList where bidList.BidListId = :BidListId")
    BidList findBidListByBidListId(int BidListId);

    BidList findBidListIdByAccount(String account);
}
