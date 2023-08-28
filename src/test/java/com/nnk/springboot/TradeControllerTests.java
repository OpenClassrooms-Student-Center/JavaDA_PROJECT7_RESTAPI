package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.LoggerApi;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private Trade trade;

    @MockBean
    private TradeRepository tradeRepository;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(TradeControllerTests.class);

    @BeforeAll
    public static void activateLoggerForTests() {
        LoggerApi loggerApi = new LoggerApi();
        loggerApi.setLoggerForTests();

    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test")
    public void testHome() throws Exception {

        mockMvc.perform(get("/trade/list")).andExpect(status().isOk());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test")
    public void testValidate() throws Exception {

        mockMvc.perform(post("/trade/validate").with(csrf())
                .param("tradeDateString", "2023-07-20T22:20")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("account", "account")
                .param("type", "type")
                .param("buyQuantity", "1")
                .param("sellQuantity", "2")
                .param("buyPrice", "3")
                .param("sellPrice", "4")
                .param("benchmark", "benchmark")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("creationName", "creationName")
                .param("revisionName", "revisionName")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testValidateWithHasError() throws Exception {

        // Type date tradeDate is error => has error
        mockMvc.perform(post("/trade/validate").with(csrf())
                .param("tradeDateString", "2023-07-20T22:20")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("tradeDate", "2023-07-20T22:20"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400

    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        Trade trade = new Trade();
        trade.setTradeId(Integer.parseInt(idString));
        trade.setAccount("account");
        Optional<Trade> optionalTrade = Optional.of(trade);

        when(tradeRepository.findById(Integer.parseInt(idString))).thenReturn(optionalTrade);
        mockMvc.perform(get("/trade/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testUpdateTrade() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/trade/update/{id}", idString).with(csrf())
                .param("tradeDateString", "2023-07-20T22:20")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("account", "account")
                .param("type", "type")
                .param("buyQuantity", "1")
                .param("sellQuantity", "2")
                .param("buyPrice", "3")
                .param("sellPrice", "4")
                .param("benchmark", "benchmark")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("creationName", "creationName")
                .param("revisionName", "revisionName")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print()).andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testUpdateTradeWithHasError() throws Exception {

        // Type date tradeDate is error => has error
        String idString = "1";
        mockMvc.perform(post("/trade/update/{id}", idString).with(
                csrf())
                .param("tradeDateString", "2023-07-20T22:20")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("tradeDate", "2023-07-20T22:20"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testDeleteTrade() throws Exception {

        String idString = "1";
        Trade trade = new Trade();
        trade.setTradeId(Integer.parseInt(idString));
        trade.setAccount("account");
        Optional<Trade> optionalTrade = Optional.of(trade);

        when(tradeRepository.findById(Integer.parseInt(idString))).thenReturn(optionalTrade);
        mockMvc.perform(get("/trade/delete/{id}", idString)).andExpect(status().isFound());
    }

}