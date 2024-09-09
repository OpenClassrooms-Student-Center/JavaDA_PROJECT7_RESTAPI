package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Trade.class, idClass = Integer.class)
public interface TradeRepository {

    List<Trade> findAll();

    Optional<Trade> findByTradeid(int id);

    Trade save(Trade trade);

    void deleteByTradeid(int trade);
}
