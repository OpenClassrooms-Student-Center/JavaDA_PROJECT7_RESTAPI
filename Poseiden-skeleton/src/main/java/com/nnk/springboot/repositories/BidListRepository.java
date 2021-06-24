package com.nnk.springboot.repositories;

import com.nnk.springboot.model.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    BidList findBidListById(int id);

    BidList findBidListIdByAccount(String account);
}
