package com.nnk.springboot.integration;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    RuleNameService ruleNameService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingAddRuleNameFormShouldReturnSuccess() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/ruleName/add"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void requestMappingHomeViewShouldReturnSuccess() throws Exception {
        //GIVEN
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL Str");
        rule.setSqlPart("SQL Part");

        List<RuleName> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(ruleList)
                .when(ruleNameService)
                .findAllRuleName();

        //WHEN
        mockMvc.perform(get("/ruleName/list"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andReturn();
        assertEquals("Template", ruleList.get(0).getTemplate());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingValidateViewShouldReturnSuccess() throws Exception {
        //GIVEN
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL Str");
        rule.setSqlPart("SQL Part");

        List<RuleName> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doNothing()
                .when(ruleNameService)
                .saveRuleName(rule);

        doReturn(ruleList)
                .when(ruleNameService)
                .findAllRuleName();
        //WHEN
        mockMvc.perform(post("/ruleName/validate")
                        .param("id", "1")
                        .param("name", "Rule Name")
                        .param("Description", "Description"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andReturn();
        assertEquals("Template", ruleList.get(0).getTemplate());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingShowUpdateFormViewShouldReturnSuccess() throws Exception {
        //GIVEN
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL Str");
        rule.setSqlPart("SQL Part");

        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());

        doReturn(rule)
                .when(ruleNameService)
                .findByRuleNameId(rule.getId());

        //WHEN
        mockMvc.perform(get("/ruleName/update/{id}", "1"))
                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();
        assertEquals("Template", rule.getTemplate());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingUpdateCurvePointViewShouldReturnSuccess() throws Exception {
        //GIVEN
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL Str");
        rule.setSqlPart("SQL Part");

        List<RuleName> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());
        doNothing()
                .when(ruleNameService)
                .saveRuleName(rule);

        doReturn(ruleList)
                .when(ruleNameService)
                .findAllRuleName();

        //WHEN
        mockMvc.perform(post("/ruleName/update/{id}", "1")
                        .param("id", "1")
                        .param("name", "Rule Name")
                        .param("Description", "Description"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andReturn();
        assertEquals("Template", ruleList.get(0).getTemplate());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingDeleteBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL Str");
        rule.setSqlPart("SQL Part");

        List<RuleName> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());

        doNothing()
                .when(ruleNameService)
                .deleteRuleName(rule);

        doReturn(ruleList)
                .when(ruleNameService)
                .findAllRuleName();

        //WHEN
        mockMvc.perform(get("/ruleName/delete/{id}", "1"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andReturn();
        assertEquals("Template", ruleList.get(0).getTemplate());
    }

}
