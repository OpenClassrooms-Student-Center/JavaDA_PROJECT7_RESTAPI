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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BidListControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BidListService bidListService;
	
	@Test
	public void test_BidListPages_NeedsAuthentication() throws Exception {
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitsBidListListAccess() throws Exception{
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitBidListListAccess() throws Exception{
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/list"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitBidListAddAccess() throws Exception{
		mockMvc.perform(get("/bidList/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitBidListAddAccess() throws Exception{
		mockMvc.perform(get("/bidList/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitBidListUpdateAccess() throws Exception{
		// saves a bidList to update in database
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		bid = bidListService.saveBidList(bid);
		
		// tries to access to update page
		mockMvc.perform(get("/bidList/update/" + bid.getBidListId()))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/update"));
		
		// deletes the bidList from the database
		bidListService.deleteBidList(bid);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitBidListUpdateAccess() throws Exception{
		// saves a bidList to update in database
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		bid = bidListService.saveBidList(bid);
		
		// tries to access to update page
		mockMvc.perform(get("/bidList/update/" + bid.getBidListId()))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/update"));
		
		// deletes the bidList from the database
		bidListService.deleteBidList(bid);
	}
			
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_DeleteABidList() throws Exception {
		// saves a bidList to delete in database
		BidList bid = new BidList("Account Test to delete", "Type Test", 10d);
		bid = bidListService.saveBidList(bid);
		
		// deletes the bidList from database
		mockMvc.perform(get("/bidList/delete/{id}", bid.getBidListId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bidList/list"));		
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotDeleteABidList_withInvalidId() throws Exception {
		
		// tries to delete a bidList from database with invalid Id
		mockMvc.perform(get("/bidList/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_AddANewValidBidList() throws Exception {
		// creates a bidList to add to database
		BidList bid = new BidList("New Account Test", "Type Test", 10D);
		
		// tries to add the bidList to database
		mockMvc
			.perform(post("/bidList/validate")
				.param("account", bid.getAccount())
				.param("type", bid.getType())
				.param("bidQuantity", bid.getBidQuantity().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bidList/list"));

		// retrieves the bidList from database
		Iterable<BidList> bidListsIterable = new ArrayList<>();
		bidListsIterable = bidListService.findAllBidLists();
		List<BidList> bidLists = new ArrayList<>();
		bidListsIterable.forEach(bidList -> bidLists.add(bidList));
		bid = bidLists.get(bidLists.size() - 1);
		
		// deletes the bidList from database
		bidListService.deleteBidList(bid);

	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotAddANewInvalidBidList() throws Exception {
		// creates an invalid bidList to add to database
		BidList bid = new BidList("Account Test", "Type Test", 0D);
		
		// tries to add the bidList to database
		mockMvc
			.perform(post("/bidList/validate")
				.param("account", bid.getAccount())
				.param("type", bid.getType())
				.param("bidQuantity", bid.getBidQuantity().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("/bidList/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UpdateValidBidList() throws Exception {
		// creates a bidList to update
		BidList bid = new BidList("Account test", "Type Test", 10d);
		bid = bidListService.saveBidList(bid);
		
		// tries to update the bidList
		mockMvc
			.perform(post("/bidList/update/{id}", bid.getBidListId())
				.param("account", "Account updated")
				.param("type", bid.getType())
				.param("bidQuantity", bid.getBidQuantity().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bidList/list"));
		
		// deletes the bidList 
		bidListService.deleteBidList(bid);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateInvalidBidList() throws Exception {
		// creates a bidList to update
		BidList bid = new BidList("Account test", "Type Test", 10d);
		bid = bidListService.saveBidList(bid);
		
		// tries to update the bidList with invalid account
		mockMvc
			.perform(post("/bidList/update/{id}", bid.getBidListId())
				.param("account", "")
				.param("type", bid.getType())
				.param("bidQuantity", bid.getBidQuantity().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("/bidList/update"));
		
		// deletes the bidList to update
		bidListService.deleteBidList(bid);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateBidList_withInvalidId() throws Exception {
		// tries to update a bidList with invalid Id
		mockMvc
			.perform(get("/bidList/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
	
}
