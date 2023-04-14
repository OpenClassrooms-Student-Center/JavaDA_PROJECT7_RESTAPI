package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TradeService{
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository){
        this. tradeRepository= tradeRepository;
    }

    public List<Trade> findAll(){
        return tradeRepository.findAll();
    }



    public Trade getTradeById(Integer i) throws Exception{

        Optional<Trade> opt= tradeRepository.findById(i);
        return opt.get();

    }

    //CREATE NEW BIDLIST
    public Trade validateNewTrade(Trade trade) throws Exception{
        return tradeRepository.save(trade);
    }
    //UPDATE BIDLIST
    public Trade updateTrade(Integer id, Trade updatedTradeEntity) throws Exception{
        Optional<Trade> opt = tradeRepository.findById(id);
        Trade formerTrade = opt.get();
        formerTrade.setAccount(updatedTradeEntity.getAccount());
        formerTrade.setBuy_quantity(updatedTradeEntity.getBuy_quantity());
        formerTrade.setType(updatedTradeEntity.getType());
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return tradeRepository.save(formerTrade);

    }


    //DELETE BIDLIST
    public void deleteTrade(Integer id) throws Exception{
        Optional<Trade> opt = tradeRepository.findById(id);
        Trade tradeToDelete = opt.get();
        tradeRepository.delete(tradeToDelete);
    }

}


