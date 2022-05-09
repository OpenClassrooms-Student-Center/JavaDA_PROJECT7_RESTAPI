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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import com.nnk.springboot.services.CurvePointService;

@WebMvcTest(controllers = CurveController.class)
class CurveControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private CurvePointService curvePointService;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	final void testCurvePointList() throws Exception {
		mockMvc.perform(get("/curvePoint/list").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("curvePoint/list"));
	}

	@Test
	@WithMockUser
	final void testAddCurveForm() throws Exception {
		mockMvc.perform(get("/curvePoint/add").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("curvePoint/add"));
	}

	@Test
	@WithMockUser
	final void testValidate() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").with(csrf().asHeader()).param("curveId", "1").param("term", "5.0")
				.param("value", "10.0")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/curvePoint/list"));
	}
	
	@Test
	@WithMockUser
	final void testValidateHasError() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").with(csrf().asHeader())
					.param("curveId", " ").param("term", "5.0")
					.param("value", "10.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"))
				.andExpect(model().hasErrors());
	}

	@Test
	@WithMockUser
	final void testShowUpdateForm() throws Exception {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(5.0);
		curvePoint.setValue(10.0);
		when(curvePointService.findById(1)).thenReturn(curvePoint);
		mockMvc.perform(get("/curvePoint/update/1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("curvePoint", curvePoint));
	}

	@Test
	@WithMockUser
	final void testUpdateCurvePoint() throws Exception {
		CurvePoint curvePoint = new CurvePoint();
		when(curvePointService.updateCurvePoint(curvePoint, 1)).thenReturn(curvePoint);
		mockMvc.perform(post("/curvePoint/update/1").with(csrf().asHeader()).param("id", "1").param("curveId", "2")
				.param("term", "5.0").param("value", "10.0"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	@WithMockUser
	final void testUpdateCurvePointHasError() throws Exception {
		mockMvc.perform(post("/curvePoint/update/1").with(csrf().asHeader())
					.param("curveId", " ").param("term", " ")
					.param("value", "10.0"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"))
				.andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser
	final void testUpdateCurvePointNotFound() throws Exception {
		when(curvePointService.updateCurvePoint(Mockito.any(CurvePoint.class), Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
		mockMvc.perform(post("/curvePoint/update/1").with(csrf().asHeader())
					.param("curveId", "1").param("term", "5.0")
					.param("value", "10.0"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().hasNoErrors());
	}

	@Test
	@WithMockUser
	final void testDeleteCurvePoint() throws Exception {
		mockMvc.perform(get("/curvePoint/delete/0").with(csrf().asHeader()))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/curvePoint/list"));
	}

}
