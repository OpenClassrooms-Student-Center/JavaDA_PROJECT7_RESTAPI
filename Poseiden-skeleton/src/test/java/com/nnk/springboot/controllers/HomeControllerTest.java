package com.nnk.springboot.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	final void testHome() throws Exception {
		mockMvc.perform(get("/").with(csrf().asHeader())).andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	@Test@WithMockUser(roles = "admin")
	final void testAdminHome() throws Exception {
		mockMvc.perform(get("/admin/home").with(csrf().asHeader())).andExpect(status().isFound())
		.andExpect(view().name("redirect:/bidList/list"));
	}

}
