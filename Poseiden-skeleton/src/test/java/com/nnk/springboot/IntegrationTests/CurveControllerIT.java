package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CurveControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurvePointService service;
    @BeforeEach
    public void setup() {
        service.deleteAllCurve();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/curvePoint/list").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("curvePoint/list"))
                .andExpect(content().string(containsString("Curve Point List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/curvePoint/add").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("curvePoint/add"))
                .andExpect(content().string(containsString("Add New Curve Point")));
    }
    @Test
    public void testPostValidate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/curvePoint/validate").with(user("user")).with(csrf())
                        .param("curveId","2")
                        .param("term","2")
                        .param("value","2.0"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, service.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(2);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(2.0);
        service.saveCurve(curvePoint);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/curvePoint/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("curvePoint/update"))
                .andExpect(content().string(containsString("2.0")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(2);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(2.0);
        service.saveCurve(curvePoint);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/curvePoint/update/"+String.valueOf(id)).with(csrf())
                        .param("term","12.0")
                        .param("value","12.0")
                        .param("id",String.valueOf(id))
                        .param("curveId","2"))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/curvePoint/list"));
        Assertions.assertEquals(12.0, service.findCurveById(id).getTerm());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(2);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(2.0);
        service.saveCurve(curvePoint);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/curvePoint/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/curvePoint/list"));
        Assertions.assertEquals(0, service.findAll().size());
    }
}
