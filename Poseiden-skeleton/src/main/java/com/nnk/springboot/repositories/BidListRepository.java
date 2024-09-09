package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = BidList.class, idClass = Integer.class)
public interface BidListRepository {
    public BidList save(BidList bidList);
    public List<BidList> findAll();
    public void deleteByBidlistid(int Bidlistid);
    public Optional<BidList> findById(Integer id);
}
