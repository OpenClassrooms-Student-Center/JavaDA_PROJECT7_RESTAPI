package com.nnk.springboot.IntegrationTests;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TradeControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TradeService service;
    @BeforeEach
    public void setup() {
        service.deleteAllTrade();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trade/list").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("trade/list"))
                .andExpect(content().string(containsString("Trade List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trade/add").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("trade/add"))
                .andExpect(content().string(containsString("Add New Trade")));
    }
    @Test
    public void testPostValidate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/trade/validate").with(user("user")).with(csrf())
                        .param("account","testAccount")
                        .param("type","testType")
                        .param("buyQuantity","2.0"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, service.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        Trade trade = new Trade();
        trade.setAccount("testAccount");
        trade.setType("testType");
        trade.setBuyQuantity(2.0);
        service.saveTrade(trade);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trade/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("trade/update"))
                .andExpect(content().string(containsString("testAccount")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        Trade trade = new Trade();
        trade.setAccount("testAccount");
        trade.setType("testType");
        trade.setBuyQuantity(2.0);
        service.saveTrade(trade);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/trade/update/"+String.valueOf(id)).with(csrf())
                        .param("id",String.valueOf(id))
                        .param("account","modifiedAccount")
                        .param("type","modifiedType")
                        .param("buyQuantity","3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/trade/list"));
        Assertions.assertEquals("modifiedAccount", service.findTradeById(id).getAccount());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        Trade trade = new Trade();
        trade.setAccount("testAccount");
        trade.setType("testType");
        trade.setBuyQuantity(2.0);
        service.saveTrade(trade);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trade/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/trade/list"));
        Assertions.assertEquals(0, service.findAll().size());
    }
}
