package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CurvePointIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        curvePointRepository.save(curvePoint);
        curvePointId = curvePoint.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        curvePointRepository.deleteById(curvePointId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = curvePointRepository.findAll().size();
    }

    public Integer databaseSizeChange() {
        return curvePointRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsCurvePoint(MvcResult result, CurvePoint curvePoint) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(curvePointId.toString())
                && resultContent.contains(curvePoint.getCurveId().toString())
                && resultContent.contains(String.valueOf(curvePoint.getTerm()))
                && resultContent.contains(String.valueOf(curvePoint.getValue()));
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/curvePoint/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsCurvePoint(result, curvePoint));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddCurvePointFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void addCurvePointFormTest () throws Exception {
            mockMvc.perform((get("/curvePoint/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void addCurvePointFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/curvePoint/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTest () throws Exception {
            mockMvc.perform(post("/curvePoint/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(curvePoint.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/curvePoint/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(curvePoint.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/curvePoint/update/" + curvePointId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsCurvePoint(result, curvePoint));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/curvePoint/update/0"))));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/curvePoint/update/" + curvePointId)))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateCurvePointTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void updateCurvePointTest () throws Exception {
            mockMvc.perform(post("/curvePoint/update/" + curvePointId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(curvePoint.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateCurvePointTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(post("/curvePoint/update/0")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(curvePoint.toString())));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateCurvePointTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/curvePoint/update/" + curvePointId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(curvePoint.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class DeleteCurvePointTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteCurvePointTest () throws Exception {
            curvePointRepository.save(curvePoint);
            mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteCurvePointTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/curvePoint/delete/0")));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteCurvePointTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/curvePoint/delete/" + curvePointId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
