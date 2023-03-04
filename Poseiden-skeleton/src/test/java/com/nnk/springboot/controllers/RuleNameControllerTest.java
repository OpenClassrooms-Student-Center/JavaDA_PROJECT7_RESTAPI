package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.service.RuleNameService;
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

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<RuleName> ruleName = Arrays.asList(new RuleName(), new RuleName(), new RuleName());
        when(ruleNameService.findAll()).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNameList", ruleName));

        verify(ruleNameService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attribute("ruleName", instanceOf(RuleNameDto.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("Name");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setJson("Json");
        ruleNameDto.setTemplate("Template");
        ruleNameDto.setSqlStr("SqlStr");
        ruleNameDto.setSqlPart("SqlPart");

        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .param("name", ruleNameDto.getName())
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).create(any(RuleNameDto.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        RuleNameDto ruleNameDto = new RuleNameDto();

        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .param("name", ruleNameDto.getName())
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name", "description", "json", "template", "sqlStr", "sqlPart"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        when(ruleNameService.findById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attribute("ruleName", ruleName));

        verify(ruleNameService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int ruleNameId = 1;
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("Name");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setJson("Json");
        ruleNameDto.setTemplate("Template");
        ruleNameDto.setSqlStr("SqlStr");
        ruleNameDto.setSqlPart("SqlPart");

        given(ruleNameService.findById(ruleNameId)).willReturn(new RuleName(ruleNameDto));

        mockMvc.perform(get("/ruleName/update/" + ruleNameId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int id = 1;
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("Name");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setJson("Json");
        ruleNameDto.setTemplate("Template");
        ruleNameDto.setSqlStr("SqlStr");
        ruleNameDto.setSqlPart("SqlPart");

        given(ruleNameService.update(eq(id), any(RuleNameDto.class))).willReturn(new RuleName(ruleNameDto));

        mockMvc.perform(post("/ruleName/update/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", ruleNameDto.getName())
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int id = 1;

        mockMvc.perform(get("/ruleName/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors());

        verify(ruleNameService).delete(id);
    }
}
