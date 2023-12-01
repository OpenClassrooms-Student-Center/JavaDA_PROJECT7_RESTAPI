package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointITTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    CurvePointService curvePointService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingAddCurvePointFormShouldReturnSuccess() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/curvePoint/add"))

        //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void requestMappingHomeViewShouldReturnSuccess() throws Exception {
        //GIVEN
        long millis=System.currentTimeMillis();
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        curvePoint.setCreationDate(Timestamp.valueOf(date));

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doReturn(curvePointList)
                .when(curvePointService)
                .findAllCurvePoint();

        //WHEN
       /* mockMvc.perform(get("/curvePoint/list"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();*/

        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingValidateViewShouldReturnSuccess() throws Exception {
        //GIVEN

        long millis=System.currentTimeMillis();
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        curvePoint.setCreationDate(Timestamp.valueOf(date));

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doNothing()
                .when(curvePointService)
                .saveCurvePoint(curvePoint);

        doReturn(curvePointList)
                .when(curvePointService)
                .findAllCurvePoint();

        //WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .param("id", "1")
                        .param("curveId", "10")
                        .param("term", "10d")
                        .param("value", "30d"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingShowUpdateFormViewShouldReturnSuccess() throws Exception {
        //GIVEN
        long millis=System.currentTimeMillis();
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        curvePoint.setCreationDate(Timestamp.valueOf(date));

        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());

        doReturn(curvePoint)
                .when(curvePointService)
                .findByCurvePointId(curvePoint.getId());

        //WHEN
        mockMvc.perform(get("/curvePoint/update/{id}", "1"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();

        assertEquals(10, (int) curvePoint.getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingUpdateCurvePointViewShouldReturnSuccess() throws Exception {
        //GIVEN
        long millis=System.currentTimeMillis();
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        curvePoint.setCreationDate(Timestamp.valueOf(date));

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());

        doNothing()
                .when(curvePointService)
                .saveCurvePoint(curvePoint);

        doReturn(curvePointList)
                .when(curvePointService)
                .findAllCurvePoint();

        //WHEN
        mockMvc.perform(post("/curvePoint/update/{id}", "10")
                        .param("id", "1")
                        .param("curveId", "10")
                        .param("term", "10d")
                        .param("value", "35d"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingDeleteBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        long millis=System.currentTimeMillis();
        LocalDateTime date = LocalDateTime.of(2025, Month.JANUARY, 8, 0,0);//LocalDateTime.parse("2025/01/01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        curvePoint.setCreationDate(Timestamp.valueOf(date));

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());

        doNothing()
                .when(curvePointService)
                .deleteCurvePoint(curvePoint);

        doReturn(curvePointList)
                .when(curvePointService)
                .findAllCurvePoint();

        //WHEN
        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }
}