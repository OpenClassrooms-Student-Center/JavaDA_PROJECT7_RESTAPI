package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class BidITTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    BidListService bidListService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingAddBidFormShouldReturnSuccess() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/bidList/add"))
        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void requestMappingHomeViewShouldReturnSuccess() throws Exception {
        //GIVEN
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type test");
        bid.setBidListId(1);
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        doReturn(bidList)
                .when(bidListService)
                .findAllBid();

        //WHEN
        /*mockMvc.perform(get("/bidList/list"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();*/
        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingValidateViewShouldReturnSuccess() throws Exception {
        //GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        doNothing()
                .when(bidListService)
                .saveBid(bid);

        doReturn(bidList)
                .when(bidListService)
                .findAllBid();

        //WHEN
        mockMvc.perform(post("/bidList/validate")
                .param("bidListId", "1")
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"))
        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andReturn();

        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingShowUpdateFormViewShouldReturnSuccess() throws Exception {
        //GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bid.getBidListId());

        doReturn(bid)
                .when(bidListService)
                .findByBidListId(bid.getBidListId());

        //WHEN
        mockMvc.perform(get("/bidList/update/{id}", "1"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
        assertEquals("Account Test", bid.getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingUpdateBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bid.getBidListId());
        doNothing()
                .when(bidListService)
                .saveBid(bid);

        doReturn(bidList)
                .when(bidListService)
                .findAllBid();

        //WHEN
        mockMvc.perform(post("/bidList/update/{id}", "1")
                .param("bidListId", "1")
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"))

        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andReturn();

        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingDeleteBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bid.getBidListId());

        doNothing()
                .when(bidListService)
                .deleteBid(bid);

        doReturn(bidList)
                .when(bidListService)
                .findAllBid();

        //WHEN
        mockMvc.perform(get("/bidList/delete/{id}", "1"))

        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andReturn();

        assertEquals("Account Test", bidList.get(0).getAccount());
    }
}
