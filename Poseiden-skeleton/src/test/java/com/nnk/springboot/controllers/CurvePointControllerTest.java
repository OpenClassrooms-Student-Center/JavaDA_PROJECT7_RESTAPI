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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CurvePointService curvePointService;
	
	@Test
	public void test_CurvePointPages_NeedAuthentication() throws Exception {
		mockMvc.perform(get("curvePoint/list"))
			.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitCurvePointListAccess() throws Exception{
		mockMvc.perform(get("/curvePoint/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitCurvePointListAccess() throws Exception{
		mockMvc.perform(get("/curvePoint/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/list"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitCurvePointAddAccess() throws Exception{
		mockMvc.perform(get("/curvePoint/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitCurvePointAddAccess() throws Exception{
		mockMvc.perform(get("/curvePoint/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitCurvePointUpdateAccess() throws Exception{
		// saves a curvePoint to update in database
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		// tries to access to update page
		mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"));
		
		// deletes the curvePoint from the database
		curvePointService.deleteCurvePoint(curvePoint);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitCurvePointUpdateAccess() throws Exception{
		// saves a curvePoint to update in database
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		// tries to access to update page
		mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"));
		
		// deletes the curvePoint from the database
		curvePointService.deleteCurvePoint(curvePoint);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_DeleteACurvePoint() throws Exception {
		// saves a curvePoint to delete in database
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		// deletes the curvePoint from database
		mockMvc.perform(get("/curvePoint/delete/{id}", curvePoint.getId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/curvePoint/list"));		
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotDeleteACurvePoint_withInvalidId() throws Exception {
		
		// tries to delete a curvePoint from database with invalid Id
		mockMvc.perform(get("/curvePoint/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_AddANewValidCurvePoint() throws Exception {
		// creates a curvePoint to add to database
		CurvePoint curvePoint = new CurvePoint(10, 10D, 30D);
		
		// tries to add the curvePoint to database
		mockMvc
			.perform(post("/curvePoint/validate")
				.param("curveId", curvePoint.getCurveId().toString())
				.param("term", curvePoint.getTerm().toString())
				.param("value", curvePoint.getValue().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/curvePoint/list"));
		
		// retrieves the curvePoint from database
		Iterable<CurvePoint> curvePointsIterable = new ArrayList<>();
		curvePointsIterable = curvePointService.findAllCurvePoints();
		List<CurvePoint> curvePoints = new ArrayList<>();
		curvePointsIterable.forEach(curve -> curvePoints.add(curve));
		curvePoint = curvePoints.get(curvePoints.size() - 1);
				
		// deletes the curvePoint from database
		curvePointService.deleteCurvePoint(curvePoint);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotAddANewInvalidCurvePoint() throws Exception {
		// creates an invalid curvePoint to add to database
		CurvePoint curvePoint = new CurvePoint(0, 10d, 30d);
		
		// tries to add the curvePoint to database
		mockMvc
			.perform(post("/curvePoint/validate")
				.param("curveId", curvePoint.getCurveId().toString())
				.param("term", curvePoint.getTerm().toString())
				.param("value", curvePoint.getValue().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("curvePoint/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UpdateValidCurvePoint() throws Exception {
		// creates a curvePoint to update
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		// tries to update the curvePoint
		mockMvc
			.perform(post("/curvePoint/update/{id}", curvePoint.getId())
				.param("curveId", curvePoint.getCurveId().toString())
				.param("term", curvePoint.getTerm().toString())
				.param("value", "20D"))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/curvePoint/list"));
		
		// deletes the curvePoint 
		curvePointService.deleteCurvePoint(curvePoint);	
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateInvalidCurvePoint() throws Exception {
		// creates a curvePoint to update
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		curvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		// tries to update the curvePoint with invalid value
		mockMvc
			.perform(post("/curvePoint/update/{id}", curvePoint.getId())
				.param("curveId", curvePoint.getCurveId().toString())
				.param("term", curvePoint.getTerm().toString())
				.param("value", ""))
			.andExpect(model().hasErrors())
			.andExpect(view().name("/curvePoint/update"));
		
		// deletes the curvePoint to update
		curvePointService.deleteCurvePoint(curvePoint);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateCurvePoint_withInvalidId() throws Exception {
		// tries to update a curvePoint with invalid Id
		mockMvc
			.perform(get("/curvePoint/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
}
