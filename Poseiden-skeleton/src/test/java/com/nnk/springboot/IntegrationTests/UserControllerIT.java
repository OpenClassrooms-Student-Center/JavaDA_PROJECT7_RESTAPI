package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.UserCustom;
import com.nnk.springboot.services.UserService;
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
public class UserControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService service;
    @BeforeEach
    public void setup() {
        service.deleteAllUser();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/list"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("user/list"))
                .andExpect(content().string(containsString("User List")));
    }
    @Test
    public void testGetAdd() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/add"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("user/add"))
                .andExpect(content().string(containsString("Add New User")));
    }
    @Test
    public void testPostValidateSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/validate").with(csrf())
                        .param("fullname","testFullname")
                        .param("username","testUsername")
                        .param("password","testPassword123*")
                        .param("role","USER"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, service.findAll().size());
        Assertions.assertFalse(service.findAll().get(0).getPassword().equals("testPassword123*"));
    }
    @Test
    public void testPostValidateNoSymbol() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/validate").with(csrf())
                        .param("fullname", "testFullname")
                        .param("username", "testUsername")
                        .param("password", "testPassword123")
                        .param("role", "USER"))
                .andExpect(status().isOk());
        Assertions.assertEquals(0, service.findAll().size());
    }
        @Test
        public void testPostValidateNoNumber() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/user/validate").with(csrf())
                            .param("fullname","testFullname")
                            .param("username","testUsername")
                            .param("password","testPassword*")
                            .param("role","USER"))
                    .andExpect(status().isOk());
            Assertions.assertEquals(0, service.findAll().size());
    }
    @Test
    public void testPostValidateNoMaj() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/validate").with(csrf())
                        .param("fullname","testFullname")
                        .param("username","testUsername")
                        .param("password","testpassword123*")
                        .param("role","USER"))
                .andExpect(status().isOk());
        Assertions.assertEquals(0, service.findAll().size());
    }
    @Test
    public void testPostValidateTooShort() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/validate").with(csrf())
                        .param("fullname","testFullname")
                        .param("username","testUsername")
                        .param("password","Test12*")
                        .param("role","USER"))
                .andExpect(status().isOk());
        Assertions.assertEquals(0, service.findAll().size());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testGetUpdate() throws Exception{
        UserCustom user = new UserCustom();
        user.setFullname("testFullname");
        user.setUsername("testUsername");
        user.setPassword("testPassword123*");
        user.setRole("USER");
        service.saveUser(user);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/update/"+String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("user/update"))
                .andExpect(content().string(containsString("testFullname")));
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testPostUpdate() throws Exception{
        UserCustom user = new UserCustom();
        user.setFullname("testFullname");
        user.setUsername("testUsername");
        user.setPassword("testPassword123*");
        user.setRole("USER");
        service.saveUser(user);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/update/"+String.valueOf(id)).with(csrf())
                        .param("id", String.valueOf(id))
                        .param("fullname", "modifiedFullname")
                        .param("username","modifiedUsername")
                        .param("password","modifiedPassword123*")
                        .param("role","USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/user/list"));
        Assertions.assertEquals("modifiedFullname", service.findUserById(id).getFullname());
    }
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testDeleteUpdate() throws Exception{
        UserCustom user = new UserCustom();
        user.setFullname("testFullname");
        user.setUsername("testUsername");
        user.setPassword("testPassword123*");
        user.setRole("USER");
        service.saveUser(user);
        int id = service.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/delete/"+String.valueOf(id)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect((ResultMatcher) view().name("redirect:/user/list"));
        Assertions.assertEquals(0, service.findAll().size());
    }
}
