package com.nnk.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration

public class RuleNameControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RuleNameService ruleNameService;
	
	@Test
	public void test_RuleNamePages_NeedAuthentication() throws Exception {
		mockMvc.perform(get("ruleName/list"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRuleNameListAccess() throws Exception{
		mockMvc.perform(get("/ruleName/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitRuleNameListAccess() throws Exception{
		mockMvc.perform(get("/ruleName/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/list"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRuleNameAddAccess() throws Exception{
		mockMvc.perform(get("/ruleName/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitRuleNameAddAccess() throws Exception{
		mockMvc.perform(get("/ruleName/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRuleNameUpdateAccess() throws Exception{
		// saves a rule to update in database
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ruleName = ruleNameService.saveRuleName(ruleName);
		
		// tries to access to update page
		mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"));
		
		// deletes the rule from the database
		ruleNameService.deleteRuleName(ruleName);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitRuleNameUpdateAccess() throws Exception{
		// saves a rule to update in database
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ruleName = ruleNameService.saveRuleName(ruleName);
		
		// tries to access to update page
		mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"));
		
		// deletes the rule from the database
		ruleNameService.deleteRuleName(ruleName);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_DeleteARuleName() throws Exception {
		// saves a rule to delete in database
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ruleName = ruleNameService.saveRuleName(ruleName);
		
		// deletes the rule from database
		mockMvc.perform(get("/ruleName/delete/{id}", ruleName.getId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));		
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotDeleteARuleName_withInvalidId() throws Exception {
		
		// tries to delete a rule from database with invalid Id
		mockMvc.perform(get("/ruleName/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_AddANewValidRuleName() throws Exception {
		// creates a rule to add to database
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		
		// tries to add the rule to database
		mockMvc
			.perform(post("/ruleName/validate")
				.param("name", ruleName.getName())
				.param("description", ruleName.getDescription())
				.param("json", ruleName.getJson())
				.param("template", ruleName.getTemplate())
				.param("sqlStr", ruleName.getSqlStr())
				.param("sqlPart", ruleName.getSqlPart()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		// retrieves the rule from database
		Iterable<RuleName> ruleNamesIterable = new ArrayList<>();
		ruleNamesIterable = ruleNameService.findAllRuleNames();
		List<RuleName> ruleNames = new ArrayList<>();
		ruleNamesIterable.forEach(bidList -> ruleNames.add(bidList));
		ruleName = ruleNames.get(ruleNames.size() - 1);
				
		// deletes the rule from database
		ruleNameService.deleteRuleName(ruleName);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotAddANewInvalidRuleName() throws Exception {
		// creates an invalid rule to add to database
		RuleName ruleName = new RuleName("", "Description", "Json", "Template", "SQL", "SQL Part");
		
		// tries to add the rule to database
		mockMvc
			.perform(post("/ruleName/validate")
				.param("name", ruleName.getName())
				.param("description", ruleName.getDescription())
				.param("json", ruleName.getJson())
				.param("template", ruleName.getTemplate())
				.param("sqlStr", ruleName.getSqlStr())
				.param("sqlPart", ruleName.getSqlPart()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("ruleName/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UpdateValidRuleName() throws Exception {
		// creates a rule to update
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ruleName = ruleNameService.saveRuleName(ruleName); 
		
		// tries to update the rule
		mockMvc
			.perform(post("/ruleName/update/{id}", ruleName.getId())
				.param("name", "Rule Name updated")
				.param("description", ruleName.getDescription())
				.param("json", ruleName.getJson())
				.param("template", ruleName.getTemplate())
				.param("sqlStr", ruleName.getSqlStr())
				.param("sqlPart", ruleName.getSqlPart()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		// deletes the rule 
		ruleNameService.deleteRuleName(ruleName);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateInvalidRuleName() throws Exception {
		// creates a rule to update
		RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ruleName = ruleNameService.saveRuleName(ruleName);
		
		// tries to update the rule with invalid name
		mockMvc
			.perform(post("/ruleName/update/{id}", ruleName.getId())
				.param("name", "")
				.param("description", ruleName.getDescription())
				.param("json", ruleName.getJson())
				.param("template", ruleName.getTemplate())
				.param("sqlStr", ruleName.getSqlStr())
				.param("sqlPart", ruleName.getSqlPart()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("/ruleName/update"));
		
		// deletes the rule to update
		ruleNameService.deleteRuleName(ruleName);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateRuleName_withInvalidId() throws Exception {
		// tries to update a rule with invalid Id
		mockMvc
			.perform(get("/ruleName/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
}
