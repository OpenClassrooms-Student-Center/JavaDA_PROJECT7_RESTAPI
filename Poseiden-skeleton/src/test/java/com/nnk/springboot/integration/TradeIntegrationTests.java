package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TradeIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        tradeRepository.save(trade);
        tradeId = trade.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        tradeRepository.deleteById(tradeId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = tradeRepository.findAll().size();
    }

    public Integer databaseSizeChange() {
        return tradeRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsTrade(MvcResult result, Trade trade) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(tradeId.toString())
                && resultContent.contains(trade.getAccount())
                && resultContent.contains(trade.getType())
                && resultContent.contains(trade.getBuyQuantity().toString());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/trade/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsTrade(result, trade));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddTradeFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void addTradeFormTest () throws Exception {
            mockMvc.perform((get("/trade/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void addTradeFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/trade/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTest () throws Exception {
            mockMvc.perform(post("/trade/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTestIfInvalidTrade () throws Exception {
            trade.setAccount(longString126);
            mockMvc.perform(post("/trade/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/trade/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/trade/update/" + tradeId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsTrade(result, trade));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/trade/update/0"))));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/trade/update/" + tradeId)))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateTradeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void updateTradeTest () throws Exception {
            mockMvc.perform(post("/trade/update/" + tradeId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateTradeTestIfInvalidTrade () throws Exception {
            trade.setAccount(longString126);
            mockMvc.perform(post("/trade/update/" + tradeId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateTradeTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(post("/trade/update/0")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(trade.toString())));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateTradeTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/trade/update/" + tradeId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(trade.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class DeleteTradeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteTradeTest () throws Exception {
            tradeRepository.save(trade);
            mockMvc.perform(get("/trade/delete/" + trade.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteTradeTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/trade/delete/0")));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteTradeTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/trade/delete/" + tradeId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
