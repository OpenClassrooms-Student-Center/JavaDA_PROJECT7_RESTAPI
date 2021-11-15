package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.BidController;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BidController.class)
@AutoConfigureMockMvc(secure = false)
public class BidControllerTest {
    @MockBean
    BidService bidService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        Bid bid = new Bid();
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(2d);
        Mockito.when(bidService.findAll()).thenReturn(Arrays.asList(bid));
        mockMvc.perform(get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(bid.getType()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(bid.getAccount()))));
    }
    @Test
    public void testAddBid() throws Exception {
        Bid bid = new Bid();
        mockMvc.perform(get("/bid/add", bid))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"));
    }

    @Test
    public void testBidValidate() throws Exception {
        Bid bid = new Bid();
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(2d);
        Mockito.when(bidService.saveOrUpdate(bid)).thenReturn(Arrays.asList(bid));
        mockMvc.perform(
        post("/bid/validate")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(
                "account=account&"+
                "type=type&"+
                "bidQuantity=1")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/bid/list"));
    }

    @Test
    public void testGetUpdateBid() throws Exception {
        Bid bid = new Bid();
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(2d);
        Mockito.when(bidService.findById(1)).thenReturn(bid);
        mockMvc.perform(get("/bid/update/1")

        )
        .andExpect(status().isOk())
        .andExpect(view().name("bid/update"));
    }

    @Test
    public void testUpdateBid() throws Exception {
        Bid bid = new Bid();
        bid.setId(1);
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(2d);
        Mockito.when(bidService.saveOrUpdate(bid)).thenReturn(Arrays.asList(bid));
        mockMvc.perform(post("/bid/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                        "account=account&"+
                        "type=type&"+
                        "bidQuantity=1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bid/list"));
    }

    @Test
    public void testDeleteBid() throws Exception {
        Bid bid = new Bid();
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(2d);
        Mockito.when(bidService.delete(2)).thenReturn(Arrays.asList(bid));
        mockMvc.perform(get("/bid/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bid/list"));
    }
}
