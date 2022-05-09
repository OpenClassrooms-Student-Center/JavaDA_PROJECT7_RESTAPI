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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import com.nnk.springboot.services.BidListService;

import Exceptions.AlreadyExistException;

@WebMvcTest(controllers = BidListController.class)
class BidListControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private BidListService bidListService;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	void testBidListList() throws Exception {
		mockMvc.perform(get("/bidList/list").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(view().name("bidList/list"));
	}

	@Test
	@WithMockUser
	void testAddBidForm() throws Exception {
		mockMvc.perform(get("/bidList/add").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
	}

	@Test
	@WithMockUser
	void testValidateAlreadyExistException() throws Exception {
		when(bidListService.createBidList(Mockito.any(BidList.class))).thenThrow(AlreadyExistException.class);
		mockMvc.perform(post("/bidList/validate").with(csrf().asHeader()).param("account", "test").param("type", "test")
				.param("bidQuantity", "10.0")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/bidList/list"));
	}
	
	@Test
	@WithMockUser
	void testValidate() throws Exception {
		mockMvc.perform(post("/bidList/validate").with(csrf().asHeader()).param("account", "test").param("type", "test")
				.param("bidQuantity", "10.0")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/bidList/list"));
	}
	
	@Test
	@WithMockUser
	void testValidateHasError() throws Exception {
		mockMvc.perform(post("/bidList/validate").with(csrf().asHeader()).param("account", "")	
					.param("type", "test")
					.param("bidQuantity", "10.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotEmpty"));
	}

	@Test
	@WithMockUser
	void testShowUpdateForm() throws Exception {
		BidList bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setAccount("test");
		bidList.setType("test");
		bidList.setBidQuantity(10.0);
		when(bidListService.findById(1)).thenReturn(bidList);
		mockMvc.perform(get("/bidList/update/1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("bidList", bidList));
				
	}

	@Test
	@WithMockUser
	void testUpdateBidHasError() throws Exception {
		mockMvc.perform(post("/bidList/update/1").with(csrf().asHeader()).param("id", "1").param("account", "")
				.param("type", "test").param("bidQuantity", "10.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotEmpty"));

	}
	
	@Test
	@WithMockUser
	void testUpdateBid() throws Exception {
		BidList bidList = new BidList();
		when(bidListService.updateBidList(bidList, 1)).thenReturn(bidList);
		mockMvc.perform(post("/bidList/update/1").with(csrf().asHeader()).param("id", "1").param("account", "test")
				.param("type", "test").param("bidQuantity", "10.0"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/bidList/list"))
				.andExpect(model().hasNoErrors());
		
	}
	
	@Test
	@WithMockUser
	void testUpdateBidListNotFound() throws Exception {
		when(bidListService.updateBidList(Mockito.any(BidList.class), Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
		mockMvc.perform(post("/bidList/update/1").with(csrf().asHeader()).param("account", "test")
						.param("type", "test").param("bidQuantity", "10.0"))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/bidList/list"))
					.andExpect(model().hasNoErrors());
	}

	@Test
	@WithMockUser
	void testDeleteBid() throws Exception {
		mockMvc.perform(get("/bidList/delete/0").with(csrf().asHeader()))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/bidList/list"));
	}

}
