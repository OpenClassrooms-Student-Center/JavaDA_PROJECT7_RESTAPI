package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BidList bidList;

    @MockBean
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testHome() throws Exception {

        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());

    }

    @Test
    public void testValidate() throws Exception {

        mockMvc.perform(post("/bidList/validate").with(csrf())
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "1")
                .param("askQuantity", "2")
                .param("bid", "3")
                .param("ask", "4")
                .param("benchmark", "benchmark")
                .param("bidListDateString", "2023-07-20T22:20")
                .param("commentary", "commentary")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("book", "book")
                .param("creationName", "creationName")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionName", "revisionName")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testValidateWithHasError() throws Exception {

        // Type number bidQuantity is error => has error
        mockMvc.perform(post("/bidList/validate").with(csrf())
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "A") // a chr
                .param("askQuantity", "2")
                .param("bid", "3")
                .param("ask", "4")
                .param("benchmark", "benchmark")
                .param("bidListDateString", "2023-07-20T22:20")
                .param("commentary", "commentary")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("book", "book")
                .param("creationName", "creationName")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionName", "revisionName")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400

    }

    @Test
    public void testValidateWithDateBlank() throws Exception {

        mockMvc.perform(post("/bidList/validate").with(csrf())
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "1")
                .param("askQuantity", "2")
                .param("bid", "3")
                .param("ask", "4")
                .param("benchmark", "benchmark")
                .param("bidListDateString", "")
                .param("commentary", "commentary")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("book", "book")
                .param("creationName", "creationName")
                .param("creationDateString", "")
                .param("revisionName", "revisionName")
                .param("revisionDateString", "")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        BidList bidList = new BidList();
        bidList.setBidlistId(Integer.parseInt(idString));
        bidList.setAccount("account");
        Optional<BidList> optionalBidList = Optional.of(bidList);

        when(bidListRepository.findById(Integer.parseInt(idString))).thenReturn(optionalBidList);
        mockMvc.perform(get("/bidList/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateBidList() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/bidList/update/{id}", idString).with(csrf())
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "1")
                .param("askQuantity", "2")
                .param("bid", "3")
                .param("ask", "4")
                .param("benchmark", "benchmark")
                .param("bidListDateString", "2023-07-20T22:20")
                .param("commentary", "commentary")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("book", "book")
                .param("creationName", "creationName")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionName", "revisionName")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print()).andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testUpdateBidListWithHasError() throws Exception {

        // Type number bidQuantity is error => has error
        String idString = "1";
        mockMvc.perform(post("/bidList/update/{id}", idString).with(
                csrf())
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "A") // a chr
                .param("askQuantity", "2")
                .param("bid", "3")
                .param("ask", "4")
                .param("benchmark", "benchmark")
                .param("bidListDateString", "2023-07-20T22:20")
                .param("commentary", "commentary")
                .param("security", "security")
                .param("status", "status")
                .param("trader", "trader")
                .param("book", "book")
                .param("creationName", "creationName")
                .param("creationDateString", "2023-07-21T22:20")
                .param("revisionName", "revisionName")
                .param("revisionDateString", "2023-07-22T22:20")
                .param("dealName", "dealName")
                .param("dealType", "dealType")
                .param("sourceListId", "sourceListId")
                .param("side", "side"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    public void testDeleteBidList() throws Exception {

        String idString = "1";
        BidList bidList = new BidList();
        bidList.setBidlistId(Integer.parseInt(idString));
        bidList.setAccount("account");
        Optional<BidList> optionalBidList = Optional.of(bidList);

        when(bidListRepository.findById(Integer.parseInt(idString))).thenReturn(optionalBidList);
        mockMvc.perform(get("/bidList/delete/{id}", idString)).andExpect(status().isFound());
    }

}