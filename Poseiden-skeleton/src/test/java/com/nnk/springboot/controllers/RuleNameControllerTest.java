package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import com.nnk.springboot.services.RuleNameService;

@WebMvcTest(controllers = RuleNameController.class)
class RuleNameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private RuleNameService ruleNameService;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	final void testRuleNameList() throws Exception {
		mockMvc.perform(get("/ruleName/list").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("ruleName/list"));
	}

	@Test
	@WithMockUser
	final void testAddRuleForm() throws Exception {
		mockMvc.perform(get("/ruleName/add").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("ruleName/add"));
	}

	@Test
	@WithMockUser
	final void testValidate() throws Exception {
		mockMvc.perform(post("/ruleName/validate").with(csrf().asHeader()).param("name", "Name")
				.param("description", "Description").param("json", "Json").param("emplate", "Template")
				.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/ruleName/list"));
	}
	
	@Test
	@WithMockUser
	void testValidateHasError() throws Exception {
		mockMvc.perform(post("/ruleName/validate").with(csrf().asHeader()).param("name", "")
				.param("description", "Description").param("json", "Json").param("emplate", "Template")
				.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart"))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/add"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"));
	}

	@Test
	@WithMockUser
	final void testShowUpdateForm() throws Exception {
		RuleName ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setName("Test");
		ruleName.setDescription("Test");
		ruleName.setJson("Test");
		ruleName.setTemplate("Test");
		ruleName.setSqlStr("Test");
		ruleName.setSqlPart("Test");
		when(ruleNameService.findById(1)).thenReturn(ruleName);
		mockMvc.perform(get("/ruleName/update/1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("ruleName", ruleName));
	}

	@Test
	@WithMockUser
	final void testUpdateRuleName() throws Exception {
		RuleName ruleName = new RuleName();
		when(ruleNameService.updateRuleName(ruleName, 1)).thenReturn(ruleName);
		mockMvc.perform(post("/ruleName/update/1").with(csrf().asHeader()).param("name", "Name")
				.param("description", "Description").param("json", "Json").param("template", "Template")
				.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	@WithMockUser
	void testUpdateRuleNameHasError() throws Exception {
		mockMvc.perform(post("/ruleName/update/1").with(csrf().asHeader()).param("name", "")
				.param("description", "Description").param("json", "Json").param("emplate", "Template")
				.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart"))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"));
	}
	
	@Test
	@WithMockUser
	void testUpdateRuleNameThrowEntityNotFoundException() throws Exception {
		when(ruleNameService.updateRuleName(Mockito.any(RuleName.class), Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
		mockMvc.perform(post("/ruleName/update/1").with(csrf().asHeader()).param("name", "Name")
				.param("description", "Description").param("json", "Json").param("emplate", "Template")
				.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().hasNoErrors());
	}

	@Test
	@WithMockUser
	final void testDeleteRuleName() throws Exception {
		mockMvc.perform(get("/ruleName/delete/0").with(csrf().asHeader()))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/ruleName/list"));
		
	}

}
