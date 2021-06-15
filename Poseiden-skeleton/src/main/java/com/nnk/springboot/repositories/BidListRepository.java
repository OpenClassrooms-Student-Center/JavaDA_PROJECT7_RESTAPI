package com.nnk.springboot.repositories;

import com.nnk.springboot.model.BidList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
