package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        ruleNameRepository.save(ruleName);
        ruleNameId = ruleName.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        ruleNameRepository.deleteById(ruleNameId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = ruleNameRepository.findAll().size();
    }

    public Integer databaseSizeChange() {
        return ruleNameRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsRuleName(MvcResult result, RuleName ruleName) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(ruleNameId.toString())
                && resultContent.contains(ruleName.getName())
                && resultContent.contains(ruleName.getDescription())
                && resultContent.contains(ruleName.getJson())
                && resultContent.contains(ruleName.getTemplate())
                && resultContent.contains(ruleName.getSqlStr())
                && resultContent.contains(ruleName.getSqlPart());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/ruleName/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRuleName(result, ruleName));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddRuleNameFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void addRuleNameFormTest () throws Exception {
            mockMvc.perform((get("/ruleName/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void addRuleNameFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/ruleName/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTest () throws Exception {
            mockMvc.perform(post("/ruleName/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTestIfInvalidRuleName () throws Exception {
            ruleName.setName(longString126);
            mockMvc.perform(post("/ruleName/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/ruleName/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/ruleName/update/" + ruleNameId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRuleName(result, ruleName));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/ruleName/update/0"))));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/ruleName/update/" + ruleNameId)))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateRuleNameTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRuleNameTest () throws Exception {
            mockMvc.perform(post("/ruleName/update/" + ruleNameId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRuleNameTestIfInvalidRuleName () throws Exception {
            ruleName.setName(longString126);
            mockMvc.perform(post("/ruleName/update/" + ruleNameId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRuleNameTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(post("/ruleName/update/0")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(ruleName.toString())));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateRuleNameTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/ruleName/update/" + ruleNameId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(ruleName.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class DeleteRuleNameTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteRuleNameTest () throws Exception {
            ruleNameRepository.save(ruleName);
            mockMvc.perform(get("/ruleName/delete/" + ruleName.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteRuleNameTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/ruleName/delete/0")));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteRuleNameTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/ruleName/delete/" + ruleNameId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
