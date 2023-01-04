package com.nnk.springboot.controllers.apiRest;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //erase database each test
@WithMockUser(username = "User")
class RuleNameApiRestControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    void showRestRuleNames() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("name", "toto");
        json.put("description", "tutu");
        json.put("json", "yes");
        json.put("template", "yes");
        json.put("sqlStr", "yes");
        json.put("sqlPart", "yes");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ruleNames/api"))
                .andExpect(status().isOk());
        //THEN
        List<RuleName> result = ruleNameRepository.findAll();
        assertEquals(1, result.size());



    }

    @Test
    void showRestRuleNameById() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("name", "toto");
        json.put("description", "tutu");
        json.put("json", "yes");
        json.put("template", "yes");
        json.put("sqlStr", "yes");
        json.put("sqlPart", "yes");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ruleName/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<RuleName> result = ruleNameRepository.findById(1);
        assertEquals("toto", result.get().getName());

    }

    @Test
    void addRestRuleName_shouldReturnGetRuleName() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("name", "toto");
        json.put("description", "tutu");
        json.put("json", "yes");
        json.put("template", "yes");
        json.put("sqlStr", "yes");
        json.put("sqlPart", "yes");


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ruleName/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<RuleName> result = ruleNameRepository.findById(1);
        assertEquals("toto", result.get().getName());
    }

    @Test
    void uploadRestRuleName() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("name", "toto");
        json.put("description", "tutu");
        json.put("json", "yes");
        json.put("template", "yes");
        json.put("sqlStr", "yes");
        json.put("sqlPart", "yes");


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        JSONObject json1 = new JSONObject();
        json1.put("id", 1);
        json1.put("name", "Frank");
        json1.put("description", "tutu");
        json1.put("json", "yes");
        json1.put("template", "yes");
        json1.put("sqlStr", "yes");
        json1.put("sqlPart", "yes");


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ruleNames/api"))
                .andExpect(status().isOk());
        //THEN
        List<RuleName> result = ruleNameRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void deleteRestRuleName_shouldReturnRuleNameDelete() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("name", "toto");
        json.put("description", "tutu");
        json.put("json", "yes");
        json.put("template", "yes");
        json.put("sqlStr", "yes");
        json.put("sqlPart", "yes");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/ruleName/api/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ruleNames/api"))
                .andExpect(status().isOk());
        //THEN
        List<RuleName> result = ruleNameRepository.findAll();
        assertEquals(0, result.size());

    }
}