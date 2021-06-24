package com.nnk.springboot.service;

import com.nnk.springboot.interfaces.TradeService;
import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    @Override
    public void validateTrade(Trade trade){

        Trade addTrade = new Trade();
        addTrade.setAccount(trade.getAccount());
        addTrade.setType(trade.getType());
        addTrade.setBuyQuantity(trade.getBuyQuantity());
        tradeRepository.save(addTrade);
    }

    @Override
    public void updateTrade(Integer id, Trade trade) {

        trade.setId(id);
        tradeRepository.save(trade);
    }

    @Override
    public void deleteTrade(Integer id) {
        Trade trade = tradeRepository.findTradeById(id);
        tradeRepository.delete(trade);
    }
}
