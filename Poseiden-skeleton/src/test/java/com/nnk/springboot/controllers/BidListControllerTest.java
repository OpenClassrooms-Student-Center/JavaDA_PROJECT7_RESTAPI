package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
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

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<BidList> bidList = Arrays.asList(new BidList(), new BidList(), new BidList());
        when(bidListService.findAll()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidList", bidList));

        verify(bidListService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().attribute("bid", instanceOf(BidListDto.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        BidListDto bidListDto = new BidListDto();
        bidListDto.setAccount("Test Account");
        bidListDto.setType("Test Type");
        bidListDto.setBidQuantity(100d);

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .param("account", bidListDto.getAccount())
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).create(any(BidListDto.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        BidListDto bidListDto = new BidListDto();

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .param("account", bidListDto.getAccount())
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().attributeHasFieldErrors("bid", "account", "type", "bidQuantity"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        when(bidListService.findById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().attribute("bid", bidList));

        verify(bidListService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int bidId = 1;
        BidListDto bid = new BidListDto();
        bid.setBidQuantity(100d);
        bid.setType("Type");

        given(bidListService.findById(bidId)).willReturn(new BidList(bid));

        mockMvc.perform(get("/bidList/update/" + bidId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bid"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int bidId = 1;
        BidListDto bid = new BidListDto();
        bid.setAccount("Account");
        bid.setBidQuantity(100d);
        bid.setType("Type");

        given(bidListService.update(eq(bidId), any(BidListDto.class))).willReturn(new BidList(bid));

        mockMvc.perform(post("/bidList/update/" + bidId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", String.valueOf(bid.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int bidId = 1;

        mockMvc.perform(get("/bidList/delete/" + bidId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());

        verify(bidListService).delete(bidId);
    }
}