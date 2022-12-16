package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.ITradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for TradeService
 */
@Service
public class TradeServiceImpl implements ITradeService {

    /**
     * SLF4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("TradeServiceImpl");


    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade findById(Integer id) throws DataNotFoundException {
        return tradeRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Trade trade) throws UsernameNotFoundException {
        logger.debug("update trade:{}", trade.getBook());
        Optional<Trade> isAlreadyUser = tradeRepository.findById(trade.getTradeId());
        if (isAlreadyUser.isPresent()) {
            tradeRepository.save(trade);
        } else {
            throw new UsernameNotFoundException("No trade " + trade + " present in dataBase ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Trade trade) {
        tradeRepository.delete(trade);

    }
}
