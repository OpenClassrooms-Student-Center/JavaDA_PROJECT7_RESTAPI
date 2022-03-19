package com.nnk.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test_UserPages_NeedAuthentication() throws Exception {
		mockMvc.perform(get("user/list"))
			.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitUserListAccess() throws Exception{
		mockMvc.perform(get("/user/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_NotPermitUserListAccess() throws Exception{
		mockMvc.perform(get("/user/list"))
			.andExpect(status().is4xxClientError())
			.andExpect(forwardedUrl("/app/error"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitUserAddAccess() throws Exception{
		mockMvc.perform(get("/user/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_NotPermitUserAddAccess() throws Exception{
		mockMvc.perform(get("/user/add"))
			.andExpect(status().is4xxClientError())
			.andExpect(forwardedUrl("/app/error"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitUserUpdateAccess() throws Exception{
		// saves a user to update in database
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		user = userService.saveUser(user);
		
		// tries to access to update page
		mockMvc.perform(get("/user/update/" + user.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"));
		
		// deletes the user from the database
		userService.deleteUser(user);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_NotPermitUserUpdateAccess() throws Exception{
		// saves a user to update in database
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		user = userService.saveUser(user);
		
		// tries to access to update page
		mockMvc.perform(get("/user/update/" + user.getId()))
			.andExpect(status().is4xxClientError())
			.andExpect(forwardedUrl("/app/error"));
		
		// deletes the user from the database
		userService.deleteUser(user);
	}
	
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_DeleteAnUser() throws Exception {
		// saves a user to delete in database
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		user = userService.saveUser(user);
		
		// deletes the user from database
		mockMvc.perform(get("/user/delete/{id}", user.getId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_NotDeleteAnUser_withInvalidId() throws Exception {
		
		// tries to delete a user from database with invalid Id
		mockMvc.perform(get("/user/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AddANewValidUser() throws Exception {
		// creates a user to add to database
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		
		// tries to add the user to database
		mockMvc
			.perform(post("/user/validate")
				.param("username", user.getUsername())
				.param("password", user.getPassword())
				.param("fullname", user.getFullname())
				.param("role", user.getRole()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		// retrieves the user from database
		Iterable<User> usersIterable = new ArrayList<>();
		usersIterable = userService.findAllUsers();
		List<User> users = new ArrayList<>();
		usersIterable.forEach(u -> users.add(u));
		user = users.get(users.size() - 1);
				
		// deletes the user from database
		userService.deleteUser(user);
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_NotAddANewInvalidUser() throws Exception {
		// creates an invalid user to add to database
		User user = new User("", "Passw0rd!", "UserTestName", "USER");
		
		// tries to add the user to database
		mockMvc
			.perform(post("/user/validate")
					.param("username", user.getUsername())
					.param("password", user.getPassword())
					.param("fullname", user.getFullname())
					.param("role", user.getRole()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("user/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_UpdateValidUser() throws Exception {
		// creates a user to update
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		user = userService.saveUser(user);
		
		// tries to update the user
		mockMvc
			.perform(post("/user/update/{id}", user.getId())
				.param("username", "UserName updated")
				.param("password", user.getPassword())
				.param("fullname", user.getFullname())
				.param("role", user.getRole()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		// deletes the user 
		userService.deleteUser(user);
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_NotUpdateInvalidUser() throws Exception {
		// creates a user to update
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");
		user = userService.saveUser(user);
		
		// tries to update the user with invalid username
		mockMvc
			.perform(post("/user/update/{id}", user.getId())
				.param("username", "")
				.param("password", user.getPassword())
				.param("fullname", user.getFullname())
				.param("role", user.getRole()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("user/update"));
		
		// deletes the user to update
		userService.deleteUser(user);
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_NotAddUser_withInvalidPassword() throws Exception {
		// creates an invalid user to add to database
		User user = new User("UserName", "password", "UserTestName", "USER");
				
		// tries to add the user to database
		mockMvc
			.perform(post("/user/validate")
				.param("username", user.getUsername())
				.param("password", user.getPassword())
				.param("fullname", user.getFullname())
				.param("role", user.getRole()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("user/add"));
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_NotUpdateUser_withInvalidId() throws Exception {
		// tries to update a user with invalid Id
		mockMvc
			.perform(get("/user/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
}
