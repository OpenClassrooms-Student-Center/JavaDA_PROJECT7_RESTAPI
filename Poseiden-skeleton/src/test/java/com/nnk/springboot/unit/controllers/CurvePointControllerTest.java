package com.nnk.springboot.unit.controllers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CurveController.class)
@AutoConfigureMockMvc(secure = false)
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        CurvePointControllerTest {
    @MockBean
    CurvePointService curvePointService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        Mockito.when(curvePointService.findAll()).thenReturn(Arrays.asList(curvePoint));
        mockMvc.perform(get("/curvepoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvepoint/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(curvePoint.getCurveId()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(curvePoint.getTerm()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(curvePoint.getValue()))));
    }

    @Test
    public void testAddCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        mockMvc.perform(get("/curvepoint/add", curvePoint))
                .andExpect(status().isOk())
                .andExpect(view().name("curvepoint/add"));
    }

    @Test
    public void testCurvePointValidate() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        Mockito.when(curvePointService.saveOrUpdate(curvePoint)).thenReturn(Arrays.asList(curvePoint));
        mockMvc.perform(
        post("/curvepoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("curveId=1&term=2&value=3")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/curvepoint/list"));
    }

    @Test
    public void testCurvePointValidateWithError() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        curvePoint.setCurveId(null);
        mockMvc.perform(post("/curvepoint/validate", curvePoint))
                .andExpect(status().isOk())
                .andExpect(view().name("curvepoint/add"))
                .andExpect(content().string(Matchers.containsString(String.valueOf("CurevId is mandatory"))));
    }

    @Test
    public void testGetUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        Mockito.when(curvePointService.findById(1)).thenReturn(curvePoint);
        mockMvc.perform(get("/curvepoint/update/1").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("curveId=1&term=2&value=3")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvepoint/update"));
    }

    @Test
    public void testUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        Mockito.when(curvePointService.saveOrUpdate(curvePoint)).thenReturn(Arrays.asList(curvePoint));
        mockMvc.perform(post("/curvepoint/update/1", curvePoint))
                .andExpect(status().isOk())
                .andExpect(view().name("curvepoint/update"));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,2d,3d);
        Mockito.when(curvePointService.delete(2)).thenReturn(Arrays.asList(curvePoint));
        mockMvc.perform(get("/curvepoint/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvepoint/list"));
    }
}
