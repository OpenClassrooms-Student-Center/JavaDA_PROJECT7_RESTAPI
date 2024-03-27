package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RuleNameService service;
    @BeforeEach
    public void setup() {
        service.deleteAllRuleName();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruleName/list").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("ruleName/list"))
                .andExpect(content().string(containsString("Rule List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruleName/add").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("ruleName/add"))
                .andExpect(content().string(containsString("Add New Rule")));
    }
    @Test
    public void testPostValidate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruleName/validate").with(user("user")).with(csrf())
                        .param("name","testName")
                        .param("description","testDescription")
                        .param("json","testJson")
                        .param("template","testTemplate")
                        .param("sql","testSql")
                        .param("sqlPart","testSqlPart"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, service.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        RuleName ruleName = new RuleName();
        ruleName.setName("testName");
        ruleName.setDescription("testDescription");
        ruleName.setJson("testJson");
        ruleName.setTemplate("testTemplate");
        ruleName.setSql("testSql");
        ruleName.setSqlPart("testSqlPart");
        service.saveRuleName(ruleName);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruleName/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("ruleName/update"))
                .andExpect(content().string(containsString("testName")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        RuleName ruleName = new RuleName();
        ruleName.setName("testName");
        ruleName.setDescription("testDescription");
        ruleName.setJson("testJson");
        ruleName.setTemplate("testTemplate");
        ruleName.setSql("testSql");
        ruleName.setSqlPart("testSqlPart");
        service.saveRuleName(ruleName);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruleName/update/"+String.valueOf(id)).with(csrf())
                        .param("id",String.valueOf(id))
                        .param("name","modifiedName")
                        .param("description","modifiedDescription")
                        .param("json","modifiedJson")
                        .param("template","modifiedTemplate")
                        .param("sql","modifiedSql")
                        .param("sqlPart","modifiedSqlPart"))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/ruleName/list"));
        Assertions.assertEquals("modifiedName", service.findRuleNameById(id).getName());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        RuleName ruleName = new RuleName();
        ruleName.setName("testName");
        ruleName.setDescription("testDescription");
        ruleName.setJson("testJson");
        ruleName.setTemplate("testTemplate");
        ruleName.setSql("testSql");
        ruleName.setSqlPart("testSqlPart");
        service.saveRuleName(ruleName);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruleName/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/ruleName/list"));
        Assertions.assertEquals(0, service.findAll().size());
    }
}
