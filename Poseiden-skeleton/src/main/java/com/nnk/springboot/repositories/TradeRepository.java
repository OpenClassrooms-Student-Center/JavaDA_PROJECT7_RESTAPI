package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.repository.CrudRepository;


public interface TradeRepository extends CrudRepository<Trade, Integer> {
}
