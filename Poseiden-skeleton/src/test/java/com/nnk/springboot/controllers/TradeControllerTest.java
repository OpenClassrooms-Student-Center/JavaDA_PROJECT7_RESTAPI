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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import com.nnk.springboot.services.TradeService;

@WebMvcTest(controllers = TradeController.class)
class TradeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private TradeService tradeService;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	final void testTradeList() throws Exception {
		mockMvc.perform(get("/trade/list").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("trade/list"));
	}

	@Test
	@WithMockUser
	final void testAddTradeForm() throws Exception {
		mockMvc.perform(get("/trade/add").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("trade/add"));
	}

	@Test
	@WithMockUser
	final void testValidate() throws Exception {
		mockMvc.perform(post("/trade/validate").with(csrf().asHeader()).param("account", "Account")
				.param("type", "Type").param("buyQuantity", "5.0"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/trade/list"));
	}
	
	@Test
	@WithMockUser
	void testValidateHasError() throws Exception {
		mockMvc.perform(post("/trade/validate").with(csrf().asHeader()).param("account", "")
				.param("type", "Type").param("buyQuantity", "5.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"))
				.andExpect(model().hasErrors());
	}

	@Test
	@WithMockUser
	final void testShowUpdateForm() throws Exception {
		Trade trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("Account");
		trade.setType("Type");
		trade.setBuyQuantity(10.0);
	    when(tradeService.findById(1)).thenReturn(trade);
	    mockMvc.perform(get("/trade/update/1").with(csrf().asHeader()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("trade", trade));
	}

	@Test
	@WithMockUser
	final void testUpdateTrade() throws Exception {
		Trade trade = new Trade();
		when(tradeService.updateTrade(trade, 1)).thenReturn(trade);
		mockMvc.perform(post("/trade/update/1").with(csrf().asHeader()).param("account", "Account")
				.param("type", "Type").param("buyQuantity", "5.0"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/trade/list"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	@WithMockUser
	void testUpdateRatingHasError() throws Exception {
		mockMvc.perform(post("/trade/update/1").with(csrf().asHeader()).param("account", "")
				.param("type", "Type").param("buyQuantity", "5.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"))
				.andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser
	void testUpdateRatingThrowEntityNotFoundException() throws Exception {
		when(tradeService.updateTrade(Mockito.any(Trade.class), Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
		mockMvc.perform(post("/trade/update/1").with(csrf().asHeader()).param("account", "Account")
				.param("type", "Type").param("buyQuantity", "5.0"))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/trade/list"))
					.andExpect(model().hasNoErrors());
	}

	@Test
	@WithMockUser
	final void testDeleteTrade() throws Exception {
		mockMvc.perform(get("/trade/delete/0").with(csrf().asHeader()))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/trade/list"));
	}

}
