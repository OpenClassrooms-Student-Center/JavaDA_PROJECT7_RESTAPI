package com.nnk.springboot.controllers.apiRest;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //erase database each test
@WithMockUser(username = "User")
class TradeApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TradeRepository tradeRepository;


    @Test
    void showAllRestTrade() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("buyQuantity", 2.0);
        json.put("sellQuantity", 1.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trades/api"))
                .andExpect(status().isOk());
        //THEN
        List<Trade> result = tradeRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void showRestTradeById() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("buyQuantity", 2.0);
        json.put("sellQuantity", 1.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trade/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<Trade> result = tradeRepository.findById(1);
        assertEquals("toto", result.get().getAccount());
    }

    @Test
    void addRestTrade() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "metal");
        json.put("buyQuantity", 2.0);
        json.put("sellQuantity", 1.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trade/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<Trade> result = tradeRepository.findById(1);
        assertEquals("metal", result.get().getType());
    }

    @Test
    void uploadRestTrade() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "metal");
        json.put("buyQuantity", 2.0);
        json.put("sellQuantity", 1.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //GIVEN
        JSONObject json1 = new JSONObject();
        json1.put("tradeId", 1);
        json1.put("account", "Jonny");
        json1.put("type", "newType");
        json1.put("buyQuantity", 3.0);
        json1.put("sellQuantity", 4.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trades/api"))
                .andExpect(status().isOk());
        //THEN
        List<Trade> result = tradeRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void deleteRestTrade() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "metal");
        json.put("buyQuantity", 2.0);
        json.put("sellQuantity", 1.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/trade/api/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/trades/api"))
                .andExpect(status().isOk());
        //THEN
        List<Trade> result = tradeRepository.findAll();
        assertEquals(0, result.size());
    }
}