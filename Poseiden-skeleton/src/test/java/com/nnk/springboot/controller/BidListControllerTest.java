package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidService bidService;

    @Test
    public void findAllBidTest() throws Exception {


        List<BidList> bidListArrayList = new ArrayList<>();
        BidList bidList = new BidList();

        bidList.setBidListId(5);
        bidList.setAccount("accountTest");
        bidList.setType("bidTypeTest");
        bidList.setBidQuantity(12d);

        bidListArrayList.add(bidList);


        when(bidService.findAll()).thenReturn(bidListArrayList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bid/list"))
                .andExpect(status().isOk())
                .andDo(print());

}

}
