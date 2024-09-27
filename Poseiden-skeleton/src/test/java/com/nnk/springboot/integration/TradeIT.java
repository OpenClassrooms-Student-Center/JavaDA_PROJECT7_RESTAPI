package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    TradeService tradeService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingAddTradeFormShouldReturnSuccess() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/trade/add"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void requestMappingHomeViewShouldReturnSuccess() throws Exception {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setCreationName("New Creation");
        trade.setTradeDate(Timestamp.valueOf(date));
        trade.setCreationDate(Timestamp.valueOf(date));

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(tradeList)
                .when(tradeService)
                .findAllTrade();

        //WHEN
        mockMvc.perform(get("/trade/list"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andReturn();
        assertEquals("Type", tradeList.get(0).getType());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingValidateViewShouldReturnSuccess() throws Exception {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setCreationName("New Creation");
        trade.setTradeDate(Timestamp.valueOf(date));
        trade.setCreationDate(Timestamp.valueOf(date));

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(true)
                .when(tradeService)
                .checkIfIdExists(trade.getTradeId());

        doNothing()
                .when(tradeService)
                .saveTrade(trade);

        doReturn(tradeList)
                .when(tradeService)
                .findAllTrade();

        //WHEN
        mockMvc.perform(post("/trade/validate")
                .param("tradeId", "1")
                .param("account", "Trade Account")
                .param("type", "Type")
                .param("creationName", "New Creation")
                .param("tradeDate", "2025-01-08 00:00:00.0")
                .param("creationDate", "2025-01-08 00:00:00.0"))
        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andReturn();
        assertEquals("Type", tradeList.get(0).getType());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingShowUpdateFormViewShouldReturnSuccess() throws Exception {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setCreationName("New Creation");
        trade.setTradeDate(Timestamp.valueOf(date));
        trade.setCreationDate(Timestamp.valueOf(date));

        doReturn(true)
                .when(tradeService)
                .checkIfIdExists(trade.getTradeId());

        doReturn(trade)
                .when(tradeService)
                .findTradeById(trade.getTradeId());

        //WHEN
        mockMvc.perform(get("/trade/update/{id}", "1"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();
        assertEquals("Type", trade.getType());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingUpdateCurvePointViewShouldReturnSuccess() throws Exception {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setCreationName("New Creation");
        trade.setTradeDate(Timestamp.valueOf(date));
        trade.setCreationDate(Timestamp.valueOf(date));

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(true)
                .when(tradeService)
                .checkIfIdExists(trade.getTradeId());

        doNothing()
                .when(tradeService)
                .saveTrade(trade);

        doReturn(tradeList)
                .when(tradeService)
                .findAllTrade();

        //WHEN
        mockMvc.perform(post("/trade/update/{id}", "28")
                .param("tradeId", "28")
                .param("account", "Trade Account")
                .param("type", "Type")
                .param("creationName", "New Creation")
                .param("tradeDate", "2025-01-08 00:00:00.0")
                .param("creationDate", "2025-01-08 00:00:00.0"))
        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andReturn();
        assertEquals("Type", tradeList.get(0).getType());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingDeleteBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setCreationName("New Creation");
        trade.setTradeDate(Timestamp.valueOf(date));
        trade.setCreationDate(Timestamp.valueOf(date));

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(true)
                .when(tradeService)
                .checkIfIdExists(trade.getTradeId());

        doNothing()
                .when(tradeService)
                .deleteTrade(trade);

        doReturn(tradeList)
                .when(tradeService)
                .findAllTrade();

        //WHEN
        mockMvc.perform(get("/trade/delete/{id}", "1"))

        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andReturn();
        assertEquals("Type", tradeList.get(0).getType());

    }

}
