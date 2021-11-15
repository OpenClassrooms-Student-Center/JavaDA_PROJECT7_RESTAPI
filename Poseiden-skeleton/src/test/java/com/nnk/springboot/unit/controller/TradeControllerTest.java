package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.impl.TradeServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TradeController.class)
@AutoConfigureMockMvc(secure = false)
public class TradeControllerTest {
    @MockBean
    TradeService tradeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        Mockito.when(tradeService.findAll()).thenReturn(Arrays.asList(trade));
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(trade.getAccount()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(trade.getType()))));
    }
    @Test
    public void testAddTrade() throws Exception {
        Trade trade = new Trade();
        mockMvc.perform(get("/trade/add", trade))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void testTradeValidate() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        Mockito.when(tradeService.saveOrUpdate(trade)).thenReturn(Arrays.asList(trade));
        mockMvc.perform(
        post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(
                "tradeId=1&"+
                "account=account&"+
                "type=type&"+
                "buyQuantity=2")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    public void testGetUpdateTrade() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        Mockito.when(tradeService.findById(1)).thenReturn(trade);
        mockMvc.perform(get("/trade/update/1").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                        "tradeId=1&"+
                        "account=account&"+
                        "type=type&"+
                        "buyQuantity=2")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("trade/update"));
    }

    @Test
    public void testUpdateTrade() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        Mockito.when(tradeService.saveOrUpdate(trade)).thenReturn(Arrays.asList(trade));
        mockMvc.perform(post("/trade/update/1", trade))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    public void testDeleteTrade() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        Mockito.when(tradeService.delete(2)).thenReturn(Arrays.asList(trade));
        mockMvc.perform(get("/trade/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }
}
