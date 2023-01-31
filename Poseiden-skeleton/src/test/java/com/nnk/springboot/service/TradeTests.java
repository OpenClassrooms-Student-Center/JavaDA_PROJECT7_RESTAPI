package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTests {

    @Autowired
    private TradeService tradeService;

    @Test
    public void tradeTest() {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        // Save
        trade = tradeService.save(trade);
        Assert.assertNotNull(trade.getTrade_Id());
        Assert.assertTrue(trade.getAccount().equals("Trade Account"));

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeService.save(trade);
        Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Find
        List<Trade> listResult = tradeService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getTrade_Id();
        tradeService.delete(trade);
        Optional<Trade> tradeList = tradeService.findById(id);
        Assert.assertFalse(tradeList.isPresent());
    }
}

