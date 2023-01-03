package com.nnk.springboot.controllers.apiRest;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //erase database each test
@WithMockUser(username = "User")
class CurveApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;


    @Test
    void showRestCurvePoint() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("curveId", 1);
        json.put("asOfDate", 1);
        json.put("term", 1);
        json.put("value", 3.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/curvePoint/api"))
                .andExpect(status().isOk());
        //THEN
        List<CurvePoint> result = curvePointRepository.findAll();
        assertEquals(3.0, result.get(0).getValue());
    }

    @Test
    void showRestCurvePointById() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("curveId", 1);
        json.put("asOfDate", 1);
        json.put("term", 1);
        json.put("value", 3.0);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/curvePoint/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<CurvePoint> result = curvePointRepository.findById(1);
        assertEquals(1, result.get().getId());
    }

    @Test
    void addRestCurvePoint() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("curveId", 1);
        json.put("asOfDate", 1);
        json.put("term", 1);
        json.put("value", 3.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/curvePoint/api"))
                .andExpect(status().isOk());
        //THEN
        List<CurvePoint> result = curvePointRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void uploadRestCurvePoint() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("curveId", 1);
        json.put("asOfDate", 1);
        json.put("term", 1);
        json.put("value", 3.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        JSONObject json1 = new JSONObject();
        json1.put("id", 1);
        json1.put("curveId", 1);
        json1.put("asOfDate", 2);
        json1.put("term", 2);
        json1.put("value", 1.0);


        mockMvc.perform(MockMvcRequestBuilders.put("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());
        //THEN
        Optional<CurvePoint> result = curvePointRepository.findById(1);
        assertEquals(1.0, result.get().getValue());

    }

    @Test
    void deleteRestCurvePoint() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("curveId", 1);
        json.put("asOfDate", 1);
        json.put("term", 1);
        json.put("value", 3.0);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.delete("/curvePoint/api/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //THEN
        List<CurvePoint> result = curvePointRepository.findAll();
        assertEquals(0, result.size());
    }
}