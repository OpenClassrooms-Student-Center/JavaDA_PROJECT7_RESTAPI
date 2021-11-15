package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.RuleController;
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.services.RuleService;
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
@WebMvcTest(controllers = RuleController.class)
@AutoConfigureMockMvc(secure = false)
public class RuleControllerTest {
    @MockBean
    RuleService ruleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        Rule rule = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.when(ruleService.findAll()).thenReturn(Arrays.asList(rule));
        mockMvc.perform(get("/rule/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getName()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getDescription()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getJson()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getSqlStr()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getSqlPart()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rule.getTemplate()))));
    }

    @Test
    public void testAddRule() throws Exception {
        Rule rule = new Rule();
        mockMvc.perform(get("/rule/add", rule))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/add"));
    }

    @Test
    public void testRuleValidate() throws Exception {
        Rule rule = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.when(ruleService.saveOrUpdate(rule)).thenReturn(Arrays.asList(rule));
        mockMvc.perform(
        post("/rule/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("name=rule&description=description&json=json&template=template&sqlStr=sql&sqlPart=sqlPart")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rule/list"));
    }

    @Test
    public void testGetUpdateRule() throws Exception {
        Rule rule = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.when(ruleService.findById(1)).thenReturn(rule);
        mockMvc.perform(get("/rule/update/1").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("name=rule&description=description&json=json&template=template&sqlStr=sql&sqlPart=sqlPart")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rule/update"));
    }

    @Test
    public void testUpdateRule() throws Exception {
        Rule rule = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.when(ruleService.saveOrUpdate(rule)).thenReturn(Arrays.asList(rule));
        mockMvc.perform(post("/rule/update/1", rule))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rule/list"));
    }

    @Test
    public void testDeleteRule() throws Exception {
        Rule rule  = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.when(ruleService.delete(2)).thenReturn(Arrays.asList(rule));
        mockMvc.perform(get("/rule/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rule/list"));
    }
}
