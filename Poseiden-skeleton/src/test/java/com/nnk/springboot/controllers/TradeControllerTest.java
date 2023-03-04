package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<Trade> trade = Arrays.asList(new Trade(), new Trade(), new Trade());
        when(tradeService.findAll()).thenReturn(trade);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("tradeList", trade));

        verify(tradeService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attribute("trade", instanceOf(TradeDto.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("A");
        tradeDto.setType("B");
        tradeDto.setBuyQuantity(4.0);

        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).create(any(TradeDto.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        TradeDto tradeDto = new TradeDto();

        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        when(tradeService.findById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attribute("trade", trade));

        verify(tradeService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int tradeId = 1;
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("A");
        tradeDto.setType("B");
        tradeDto.setBuyQuantity(4.0);

        given(tradeService.findById(tradeId)).willReturn(new Trade(tradeDto));

        mockMvc.perform(get("/trade/update/" + tradeId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trade"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int id = 1;
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("A");
        tradeDto.setType("B");
        tradeDto.setBuyQuantity(4.0);

        given(tradeService.update(eq(id), any(TradeDto.class))).willReturn(new Trade(tradeDto));

        mockMvc.perform(post("/trade/update/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int id = 1;

        mockMvc.perform(get("/trade/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors());

        verify(tradeService).delete(id);
    }

}


