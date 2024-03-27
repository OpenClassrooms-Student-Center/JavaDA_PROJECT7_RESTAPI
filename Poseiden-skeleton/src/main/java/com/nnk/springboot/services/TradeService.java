package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private TradeRepository tradeRepository;
    public TradeService(TradeRepository tradeRepository) {this.tradeRepository = tradeRepository;}

    public void saveTrade(Trade trade){tradeRepository.save(trade);}
    public List<Trade> findAll(){return tradeRepository.findAll();}
    public Trade findTradeById(Integer id){
        Optional<Trade> trade = tradeRepository.findById(id);
        if(trade.isPresent()){
            return trade.get();
        }
        return null;
    }
    public void deleteTrade(Trade trade){tradeRepository.delete(trade);}
    public void deleteAllTrade(){tradeRepository.deleteAll();}

}
