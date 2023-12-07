package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        bidListRepository.save(bidList);
        bidListId = bidList.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        bidListRepository.deleteById(bidListId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = bidListRepository.findAll().size();
    }

    public Integer databaseSizeChange() {
        return bidListRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsBidList(MvcResult result, BidList bidList) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(bidListId.toString())
                && resultContent.contains(bidList.getAccount())
                && resultContent.contains(bidList.getType())
                && resultContent.contains(bidList.getBidQuantity().toString());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/bidList/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsBidList(result, bidList));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddBidListFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void addBidListFormTest () throws Exception {
            mockMvc.perform((get("/bidList/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void addBidListFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/bidList/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTest () throws Exception {
            mockMvc.perform(post("/bidList/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTestIfInvalidBidList () throws Exception {
            bidList.setAccount(longString126);
            mockMvc.perform(post("/bidList/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/bidList/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/bidList/update/" + bidListId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsBidList(result, bidList));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/bidList/update/0"))));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/bidList/update/" + bidListId)))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateBidListTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void updateBidListTest () throws Exception {
            mockMvc.perform(post("/bidList/update/" + bidListId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateBidListTestIfInvalidBidList () throws Exception {
            bidList.setAccount(longString126);
            mockMvc.perform(post("/bidList/update/" + bidListId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateBidListTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(post("/bidList/update/0")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(bidList.toString())));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateBidListTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/bidList/update/" + bidListId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(bidList.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
    @Nested
    public class DeleteBidListTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteBidListTest () throws Exception {
            bidListRepository.save(bidList);
            mockMvc.perform(get("/bidList/delete/" + bidList.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteBidListTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/bidList/delete/0")));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteBidListTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/bidList/delete/" + bidListId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
