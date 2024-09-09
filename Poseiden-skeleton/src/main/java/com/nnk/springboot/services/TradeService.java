package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * TradeService
 *
 * The service for the trade operations. Injects the Trade repository.
 *
 */
@Service
@RequiredArgsConstructor
public class TradeService {

    /** The trade repository. */
    private final TradeRepository tradeRepository;

    /**
     * Save the trade.
     *
     * @param trade the trade
     * @return the saved trade
     */
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Delete the trade.
     *
     * @param trade the trade id
     */
    @Transactional
    public void delete(int trade) {
        tradeRepository.deleteByTradeid(trade);
    }

    /**
     * Find all trades.
     *
     * @return the list of trades
     */
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * Find the trade by id.
     *
     * @param  id the trade id
     * @return    the trade
     * @throws RequestedObjectNotFoundException if the trade is not found
     */
    public Trade findById(Integer id) throws RequestedObjectNotFoundException {
        return tradeRepository.findByTradeid(id)
                .orElseThrow(() -> new RequestedObjectNotFoundException("Trade not found with id: " + id));
    }
}

