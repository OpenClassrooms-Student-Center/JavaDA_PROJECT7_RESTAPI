package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * The type Trade service.
 */
@Service
@AllArgsConstructor
public class TradeServiceImpl extends AbstractCrudService<Trade> implements TradeService {
    /**
     * The Repository.
     */
    TradeRepository repository;

    @Override
    protected JpaRepository<Trade, Integer> getRepository() {
        return repository;
    }
}
