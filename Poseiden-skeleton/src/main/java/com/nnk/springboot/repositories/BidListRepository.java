package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.BidList;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
