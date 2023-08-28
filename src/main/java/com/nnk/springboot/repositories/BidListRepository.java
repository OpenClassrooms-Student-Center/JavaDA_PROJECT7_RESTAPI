package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.BidList;

public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
