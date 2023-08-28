package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.LoggerApi;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RuleName ruleName;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(RuleNameControllerTests.class);

    @BeforeAll
    public static void activateLoggerForTests() {
        LoggerApi loggerApi = new LoggerApi();
        loggerApi.setLoggerForTests();

    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test")
    public void testHome() throws Exception {

        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test")
    public void testValidate() throws Exception {

        mockMvc.perform(post("/ruleName/validate").with(csrf())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testValidateWithHasError() throws Exception {

        // Values name is empty => has error
        mockMvc.perform(post("/ruleName/validate").with(csrf())
                .param("name", "")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400

    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        RuleName ruleName = new RuleName();
        ruleName.setId(Integer.parseInt(idString));
        ruleName.setName("name");
        Optional<RuleName> optionalRuleName = Optional.of(ruleName);

        when(ruleNameRepository.findById(Integer.parseInt(idString))).thenReturn(optionalRuleName);
        mockMvc.perform(get("/ruleName/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testUpdateRuleName() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/ruleName/update/{id}", idString).with(csrf())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart"))
                .andDo(print()).andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testUpdateRuleNameWithHasError() throws Exception {

        // Values name is empty => has error
        String idString = "1";
        mockMvc.perform(post("/ruleName/update/{id}", idString).with(
                csrf())
                .param("name", "")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testDeleteRuleName() throws Exception {

        String idString = "1";
        RuleName ruleName = new RuleName();
        ruleName.setId(Integer.parseInt(idString));
        ruleName.setName("name");
        Optional<RuleName> optionalRuleName = Optional.of(ruleName);

        when(ruleNameRepository.findById(Integer.parseInt(idString))).thenReturn(optionalRuleName);
        mockMvc.perform(get("/ruleName/delete/{id}", idString)).andExpect(status().isFound());
    }

}