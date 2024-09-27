package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    List<BidList> findAll();

    BidList save(BidList bid);

    void delete(BidList bid);

    Optional<BidList> findById(Integer id);

    BidList findByBidListId(Integer id);

    @Override
    boolean existsById(Integer integer);
}
