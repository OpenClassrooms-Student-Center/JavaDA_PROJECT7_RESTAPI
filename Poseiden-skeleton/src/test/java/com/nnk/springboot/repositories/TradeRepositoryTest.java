package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeRepositoryTest {
    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    private void setup() {
        tradeRepository = Mockito.mock(TradeRepository.class);
    }

    @DisplayName(value = "1°) Recherche de tous les Trade")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_Trade() {
        List<Trade> tradeList = new ArrayList<>();

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        Trade trade4 = new Trade();
        Trade trade5 = new Trade();

        tradeList.add(trade1);
        tradeList.add(trade2);
        tradeList.add(trade3);
        tradeList.add(trade4);
        tradeList.add(trade5);

        when(tradeRepository.findAll()).thenReturn(tradeList);

        List<Trade> result = tradeRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(tradeList.size());
    }

    @DisplayName(value = "2°) Recherche de Trade par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findTrade_By_Id() {
        Trade trade = new Trade(1, "accountTest", "typeTest", 5.00, 10.00, 30.00,
                60.00, "benchmarkTest", new Timestamp(new Date().getTime()), "securityTest", "statusTest",
                "traderTest", "bookTest", "creationNameTest", new Timestamp(new Date().getTime()),
                "revisionNameTest", new Timestamp(new Date().getTime()), "dealNameTest", "dealTypeTest",
                "sourceListIdTest", "sideTest");

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

        Optional<Trade> tradeResult = tradeRepository.findById(trade.getTradeId());

        assertThat(tradeResult).isNotNull();
        assertThat(tradeResult).isPresent();
        assertThat(tradeResult.get().getTradeId()).isEqualTo(trade.getTradeId());
    }

    @DisplayName(value = "3°) Mise à jour d'un Trade Existant")
    @Order(3)
    @Test
    void test_update_should_update_Trade() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(5.00);
        trade.setSellQuantity(10.00);
        trade.setBuyPrice(30.00);
        trade.setSellPrice(60.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(new Timestamp(new Date().getTime()));
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(new Timestamp(new Date().getTime()));
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(new Timestamp(new Date().getTime()));
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        Trade tradeUpdated = new Trade();
        tradeUpdated.setTradeId(1);
        tradeUpdated.setAccount("accountTestUpdated");
        tradeUpdated.setType("typeTestUpdated");
        tradeUpdated.setBuyQuantity(15.00);
        tradeUpdated.setSellQuantity(20.00);
        tradeUpdated.setBuyPrice(40.00);
        tradeUpdated.setSellPrice(80.00);
        tradeUpdated.setBenchmark("benchmarkTestUpdated");
        tradeUpdated.setTradeDate(new Timestamp(new Date().getTime()));
        tradeUpdated.setSecurity("securityTestUpdated");
        tradeUpdated.setStatus("statusTestUpdated");
        tradeUpdated.setTrader("traderTestUpdated");
        tradeUpdated.setBook("bookTestUpdated");
        tradeUpdated.setCreationName("creationNameTestUpdated");
        tradeUpdated.setCreationDate(new Timestamp(new Date().getTime()));
        tradeUpdated.setRevisionName("revisionNameTestUpdated");
        tradeUpdated.setRevisionDate(new Timestamp(new Date().getTime()));
        tradeUpdated.setDealName("dealNameTestUpdated");
        tradeUpdated.setDealType("dealTypeTestUpdated");
        tradeUpdated.setSourceListId("sourceListIdTestUpdated");
        tradeUpdated.setSide("sideTestUpdated");

        when(tradeRepository.save(any(Trade.class))).thenReturn(tradeUpdated);

        Trade tradeResult = tradeRepository.save(trade);

        assertThat(tradeResult).isNotNull();
        assertThat(tradeResult.getTradeId()).isEqualTo(tradeUpdated.getTradeId());
        assertThat(tradeResult.getAccount()).isEqualTo(tradeUpdated.getAccount());
        assertThat(tradeResult.getType()).isEqualTo(tradeUpdated.getType());
        assertThat(tradeResult.getBuyQuantity()).isEqualTo(tradeUpdated.getBuyQuantity());
        assertThat(tradeResult.getSellQuantity()).isEqualTo(tradeUpdated.getSellQuantity());
        assertThat(tradeResult.getBuyPrice()).isEqualTo(tradeUpdated.getBuyPrice());
        assertThat(tradeResult.getSellPrice()).isEqualTo(tradeUpdated.getSellPrice());
        assertThat(tradeResult.getBenchmark()).isEqualTo(tradeUpdated.getBenchmark());
        assertThat(tradeResult.getTradeDate()).isEqualTo(tradeUpdated.getTradeDate());
        assertThat(tradeResult.getSecurity()).isEqualTo(tradeUpdated.getSecurity());
        assertThat(tradeResult.getStatus()).isEqualTo(tradeUpdated.getStatus());
        assertThat(tradeResult.getTrader()).isEqualTo(tradeUpdated.getTrader());
        assertThat(tradeResult.getBook()).isEqualTo(tradeUpdated.getBook());
        assertThat(tradeResult.getCreationName()).isEqualTo(tradeUpdated.getCreationName());
        assertThat(tradeResult.getCreationDate()).isEqualTo(tradeUpdated.getCreationDate());
        assertThat(tradeResult.getRevisionName()).isEqualTo(tradeUpdated.getRevisionName());
        assertThat(tradeResult.getRevisionDate()).isEqualTo(tradeUpdated.getRevisionDate());
        assertThat(tradeResult.getDealName()).isEqualTo(tradeUpdated.getDealName());
        assertThat(tradeResult.getDealType()).isEqualTo(tradeUpdated.getDealType());
        assertThat(tradeResult.getSourceListId()).isEqualTo(tradeUpdated.getSourceListId());
        assertThat(tradeResult.getSide()).isEqualTo(tradeUpdated.getSide());
    }

    @DisplayName(value = "4°) Suppression de Trade par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteTrade_By_Id() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(5.00);
        trade.setSellQuantity(10.00);
        trade.setBuyPrice(30.00);
        trade.setSellPrice(60.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(new Timestamp(new Date().getTime()));
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(new Timestamp(new Date().getTime()));
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(new Timestamp(new Date().getTime()));
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        tradeRepository.deleteById(trade.getTradeId());
    }
}
