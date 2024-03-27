package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BidListControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BidListService bidListService;
    @BeforeEach
    public void setup() {
        bidListService.deleteAllBidList();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/bidList/list").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("bidList/list"))
                .andExpect(content().string(containsString("Bid List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bidList/add").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("bidList/add"))
                .andExpect(content().string(containsString("Add New Bid")));
    }
    @Test
    public void testPostValidate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bidList/validate").with(user("user")).with(csrf())
                        .param("account","testAccount")
                        .param("type","testType")
                        .param("bidQuantity","3.0"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, bidListService.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        BidList bidList = new BidList();
        bidList.setAccount("TestAccount");
        bidList.setType("TestType");
        bidList.setBidQuantity(3.0);
        bidListService.saveBidList(bidList);
        int id = bidListService.findAll().get(0).getBidListId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bidList/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("bidList/update"))
                .andExpect(content().string(containsString("TestAccount")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        BidList bidList = new BidList();
        bidList.setAccount("TestAccount");
        bidList.setType("TestType");
        bidList.setBidQuantity(3.0);
        bidListService.saveBidList(bidList);
        int id = bidListService.findAll().get(0).getBidListId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bidList/update/"+String.valueOf(id)).with(csrf())
                        .param("account","ChangedAccount")
                        .param("type","ChangedType")
                        .param("bidQuantity","4.0")
                        .param("bidListId",String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/bidList/list"));
        Assertions.assertEquals("ChangedAccount", bidListService.findBidListById(id).getAccount());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        BidList bidList = new BidList();
        bidList.setAccount("TestAccount");
        bidList.setType("TestType");
        bidList.setBidQuantity(3.0);
        bidListService.saveBidList(bidList);
        int id = bidListService.findAll().get(0).getBidListId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bidList/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/bidList/list"));
        Assertions.assertEquals(0, bidListService.findAll().size());
    }
}