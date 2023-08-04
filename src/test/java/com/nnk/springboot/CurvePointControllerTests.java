package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CurvePoint curvePoint;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testHome() throws Exception {

        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());

    }

    @Test
    public void testValidate() throws Exception {

        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                .param("asOfDateString", "2023-07-20T22:20")
                .param("curveId", "1")
                .param("term", "2")
                .param("value", "3")
                .param("creationDateString", "2023-07-21T22:20"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testValidateNoDates() throws Exception {

        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                .param("asOfDateString", "")
                .param("curveId", "1")
                .param("term", "2")
                .param("value", "3")
                .param("creationDateString", ""))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testValidateWithHasError() throws Exception {

        // Type integer curveId is error => has error
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                .param("asOfDateString", "2023-07-20T22:20")
                .param("curveId", "a")
                .param("term", "2")
                .param("value", "3")
                .param("creationDateString", "2023-07-21T22:20"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(Integer.parseInt(idString));
        curvePoint.setValue(2.0);
        Optional<CurvePoint> optionalCurvePoint = Optional.of(curvePoint);

        when(curvePointRepository.findById(Integer.parseInt(idString))).thenReturn(optionalCurvePoint);
        mockMvc.perform(get("/curvePoint/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateCurvePoint() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/curvePoint/update/{id}", idString).with(csrf())
                .param("asOfDateString", "2023-07-20T22:20")
                .param("curveId", "1")
                .param("term", "2")
                .param("value", "3")
                .param("creationDateString", "2023-07-21T22:20"))
                .andDo(print()).andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testUpdateCurvePointWithHasError() throws Exception {

        // Type integer curveId is error => has error
        String idString = "1";
        mockMvc.perform(post("/curvePoint/update/{id}", idString).with(
                csrf())
                .param("asOfDateString", "2023-07-20T22:20")
                .param("curveId", "a")
                .param("term", "2")
                .param("value", "3")
                .param("creationDateString", "2023-07-21T22:20"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {

        String idString = "1";
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(Integer.parseInt(idString));
        curvePoint.setValue(2.0);
        Optional<CurvePoint> optionalCurvePoint = Optional.of(curvePoint);

        when(curvePointRepository.findById(Integer.parseInt(idString))).thenReturn(optionalCurvePoint);
        mockMvc.perform(get("/curvePoint/delete/{id}", idString)).andExpect(status().isFound());
    }

}