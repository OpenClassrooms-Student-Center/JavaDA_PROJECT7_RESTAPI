package com.poseidon.api.repositories;

import com.poseidon.api.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findBidById(Integer id);

}
