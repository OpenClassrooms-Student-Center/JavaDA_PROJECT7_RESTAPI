package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	final void testHome() throws Exception {
		mockMvc.perform(get("/user/list")).andExpect(status().isOk())
			.andExpect(view().name("user/list"));
	}

	@Test
	final void testAddUser() throws Exception {
		mockMvc.perform(get("/user/add")).andExpect(status().isOk())
			.andExpect(view().name("user/add"));
	}

	@Test
	@WithMockUser
	final void testValidate() throws Exception {
		mockMvc.perform(post("/user/validate").with(csrf().asHeader()).param("fullname", "Fullname")
				.param("username", "username").param("password", "Password@1")
				.param("role", "user"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/user/list"));
	}
	
	@Test
	void testValidateHasError() throws Exception {
		mockMvc.perform(post("/user/validate").with(csrf().asHeader()).param("fullname", "")
				.param("username", "username").param("password", "Password@1")
				.param("role", "user"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
	}

	@Test
	@WithMockUser
	final void testShowUpdateForm() throws Exception {
		User user = new User();
		user.setId(1);
		user.setFullname("Fullname");
		user.setUsername("Username");
		user.setPassword("Password@1");
		user.setRole("user");
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/user/update/1").with(csrf().asHeader()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attribute("user", user));
	}

	@Test
	@WithMockUser
	final void testUpdateUser() throws Exception {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);
		mockMvc.perform(post("/user/update/4").with(csrf().asHeader()).param("fullname", "Fullname")
				.param("username", "username").param("password", "Password@1")
				.param("role", "user"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/user/list"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	@WithMockUser
	final void testUpdateUserHasError() throws Exception {
		mockMvc.perform(post("/user/update/1").with(csrf().asHeader()).param("fullname", "")
				.param("username", "username").param("password", "Password@1")
				.param("role", "user"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/update"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
	}

	@Test
	@WithMockUser
	final void testDeleteUser() throws Exception {
		User user = new User();
		user.setId(1);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/user/delete/1").with(csrf().asHeader()))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/user/list"));
	}

}
