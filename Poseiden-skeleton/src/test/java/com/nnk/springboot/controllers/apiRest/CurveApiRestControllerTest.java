package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
    void showRestCurvePoint() {
    }

    @Test
    void showRestCurvePointById() {
    }

    @Test
    void addRestCurvePoint() {
    }

    @Test
    void uploadRestCurvePoint() {
    }

    @Test
    void deleteRestCurvePoint() {
    }
}