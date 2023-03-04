package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurveController.class)
class CurveControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<CurvePoint> curvePoint = Arrays.asList(new CurvePoint(), new CurvePoint(), new CurvePoint());
        when(curvePointService.findAll()).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curveList", curvePoint));

        verify(curvePointService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", instanceOf(CurvePointDto.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(1);
        curvePointDto.setTerm(2.0);
        curvePointDto.setValue(3.0);

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).create(any(CurvePointDto.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        CurvePointDto curvePointDto = new CurvePointDto();

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId", "term", "value"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        when(curvePointService.findById(1)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", curvePoint));

        verify(curvePointService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int curvePointId = 1;
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(1);
        curvePointDto.setTerm(2.0);
        curvePointDto.setValue(3.0);

        given(curvePointService.findById(curvePointId)).willReturn(new CurvePoint(curvePointDto));

        mockMvc.perform(get("/curvePoint/update/" + curvePointId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int id = 1;
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(1);
        curvePointDto.setTerm(2.0);
        curvePointDto.setValue(3.0);

        given(curvePointService.update(eq(id), any(CurvePointDto.class))).willReturn(new CurvePoint(curvePointDto));

        mockMvc.perform(post("/curvePoint/update/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int id = 1;

        mockMvc.perform(get("/curvePoint/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().hasNoErrors());

        verify(curvePointService).delete(id);
    }
}