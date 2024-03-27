package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingContollerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingService service;
    @BeforeEach
    public void setup() {
        service.deleteAllRating();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rating/list").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("rating/list"))
                .andExpect(content().string(containsString("Rating List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rating/add").with(user("user")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("rating/add"))
                .andExpect(content().string(containsString("Add New Rating")));
    }
    @Test
    public void testPostValidate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rating/validate").with(user("user")).with(csrf())
                        .param("moodysRating","testMoodysRating")
                        .param("sandPRating","testSandPRating")
                        .param("fitchRating","testFitchRating")
                        .param("orderNumber","2"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, service.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        Rating rating = new Rating();
        rating.setMoodysRating("testMoodysRating");
        rating.setSandPRating("testSandPRating");
        rating.setFitchRating("testFitchRating");
        rating.setOrderNumber(2);
        service.saveRating(rating);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rating/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("rating/update"))
                .andExpect(content().string(containsString("testMoodysRating")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        Rating rating = new Rating();
        rating.setMoodysRating("testMoodysRating");
        rating.setSandPRating("testSandPRating");
        rating.setFitchRating("testFitchRating");
        rating.setOrderNumber(2);
        service.saveRating(rating);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rating/update/"+String.valueOf(id)).with(csrf())
                        .param("id",String.valueOf(id))
                        .param("moodysRating","modifiedMoodysRating")
                        .param("sandPRating","modifiedSandPRating")
                        .param("fitchRating","modifiedFitchRating")
                        .param("orderNumber","3"))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/rating/list"));
        Assertions.assertEquals("modifiedMoodysRating", service.findRatingById(id).getMoodysRating());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        Rating rating = new Rating();
        rating.setMoodysRating("testMoodysRating");
        rating.setSandPRating("testSandPRating");
        rating.setFitchRating("testFitchRating");
        rating.setOrderNumber(2);
        service.saveRating(rating);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rating/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/rating/list"));
        Assertions.assertEquals(0, service.findAll().size());
    }
}
