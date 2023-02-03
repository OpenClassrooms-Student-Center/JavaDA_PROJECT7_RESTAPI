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
    public Optional<Trade> findById(Integer id) throws DataNotFoundException {
        logger.debug("find bidById:{}", id);
        return Optional.ofNullable(tradeRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Invalid bid Id: {} ", id);
            return new DataNotFoundException("No User with id " + id + " found ");
        }));
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Trade save(Trade trade) {
        logger.debug("save trade:{}", trade.getTradeId());
        tradeRepository.save(trade);
        return trade;
    }


    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Trade update(Trade trade) throws UsernameNotFoundException {
        logger.debug("update trade:{}", trade.getBook());
//        Optional<Trade> isAlreadyUser = tradeRepository.findById(trade.getTradeId());
//        if (isAlreadyUser.isPresent()) {
        Trade deleteTrade = tradeRepository.findById(trade.getTradeId()).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + trade + " Not Present in Data Base");
        });
            tradeRepository.save(deleteTrade);

        return trade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer trade) {
        logger.debug("delete trade:{}", trade);
        Trade deleteTrade = tradeRepository.findById(trade).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + trade + " Not Present in Data Base");
        });

        tradeRepository.deleteById(deleteTrade.getTradeId());
    }
}
