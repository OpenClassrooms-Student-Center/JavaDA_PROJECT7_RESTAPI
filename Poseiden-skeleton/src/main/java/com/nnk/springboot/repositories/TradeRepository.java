package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    List<Trade> findAll();

    Trade save(Trade trade);

    void delete(Trade trade);

    Optional<Trade> findById(Integer id);

    Trade findByTradeId(Integer id);

    @Override
    boolean existsById(Integer integer);
}
