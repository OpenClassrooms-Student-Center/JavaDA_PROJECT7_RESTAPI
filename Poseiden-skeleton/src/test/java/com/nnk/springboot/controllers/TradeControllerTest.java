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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration

public class TradeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TradeService tradeService;
	
	@Test
	public void test_TradePages_NeedAuthentication() throws Exception {
		mockMvc.perform(get("trade/list"))
			.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitTradeListAccess() throws Exception{
		mockMvc.perform(get("/trade/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitTradeListAccess() throws Exception{
		mockMvc.perform(get("/trade/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/list"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitTradeAddAccess() throws Exception{
		mockMvc.perform(get("/trade/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitTradeAddAccess() throws Exception{
		mockMvc.perform(get("/trade/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitTradeUpdateAccess() throws Exception{
		// saves a trade to update in database
		Trade trade = new Trade("Trade Account", "Type", 10D);
		trade = tradeService.saveTrade(trade);
		
		// tries to access to update page
		mockMvc.perform(get("/trade/update/" + trade.getTradeId()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"));
		
		// deletes the trade from the database
		tradeService.deleteTrade(trade);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitTradeUpdateAccess() throws Exception{
		// saves a trade to update in database
		Trade trade = new Trade("Trade Account", "Type", 10D);
		trade = tradeService.saveTrade(trade);
		
		// tries to access to update page
		mockMvc.perform(get("/trade/update/" + trade.getTradeId()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"));
		
		// deletes the trade from the database
		tradeService.deleteTrade(trade);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_DeleteATrade() throws Exception {
		// saves a trade to update in database
		Trade trade = new Trade("Trade Account", "Type", 10D);
		trade = tradeService.saveTrade(trade);
		
		// deletes the trade from database
		mockMvc.perform(get("/trade/delete/{id}", trade.getTradeId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotDeleteATrade_withInvalidId() throws Exception {
		
		// tries to delete a trade from database with invalid Id
		mockMvc.perform(get("/trade/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_AddANewValidTrade() throws Exception {
		// creates a trade to add to database
		Trade trade = new Trade("Trade Account", "Type", 10D);
		
		// tries to add the trade to database
		mockMvc
			.perform(post("/trade/validate")
				.param("account", trade.getAccount())
				.param("type", trade.getType())
				.param("buyQuantity", trade.getBuyQuantity().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		// retrieves the trade from database
		Iterable<Trade> tradesIterable = new ArrayList<>();
		tradesIterable = tradeService.findAllTrades();
		List<Trade> trades = new ArrayList<>();
		tradesIterable.forEach(t -> trades.add(t));
		trade = trades.get(trades.size() - 1);
				
		// deletes the trade from database
		tradeService.deleteTrade(trade);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotAddANewInvalidTrade() throws Exception {
		// creates an invalid trade to add to database
		Trade trade = new Trade("Account", "Type", 0D);
		
		// tries to add the trade to database
		mockMvc
			.perform(post("/trade/validate")
				.param("account", trade.getAccount())
				.param("type", trade.getType())
				.param("buyQuantity", trade.getBuyQuantity().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("trade/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UpdateValidTrade() throws Exception {
		// creates a trade to update
		Trade trade = new Trade("Trade Account", "Type", 10D);
		trade = tradeService.saveTrade(trade);
		
		// tries to update the trade
		mockMvc
			.perform(post("/trade/update/{id}", trade.getTradeId())
				.param("account", "Trade Account updated")
				.param("type", trade.getType())
				.param("buyQuantity", trade.getBuyQuantity().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		// deletes the trade 
		tradeService.deleteTrade(trade);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateInvalidTrade() throws Exception {
		// creates a trade to update
		Trade trade = new Trade("Trade Account", "Type", 10D);
		trade = tradeService.saveTrade(trade);
		
		// tries to update the trade with invalid account
		mockMvc
			.perform(post("/trade/update/{id}", trade.getTradeId())
				.param("account", "")
				.param("type", trade.getType())
				.param("buyQuantity", trade.getBuyQuantity().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("trade/update"));
		
		// deletes the trade to update
		tradeService.deleteTrade(trade);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateTrade_withInvalidId() throws Exception {
		// tries to update a bidList with invalid Id
		mockMvc
			.perform(get("/trade/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
}
