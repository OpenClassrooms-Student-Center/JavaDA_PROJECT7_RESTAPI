package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
public class UserIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        userRepository.save(user);
        userId = user.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        userRepository.deleteById(userId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = userRepository.findAll().size();
    }

    public Integer databaseSizeChange() {
        return userRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsUser(MvcResult result, User user) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(userId.toString())
                && resultContent.contains(user.getUsername())
                && resultContent.contains(user.getFullname())
                && resultContent.contains(user.getRole());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/user/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsUser(result, user));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/user/list"))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddUserFormTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void addUserFormTest () throws Exception {
            mockMvc.perform((get("/user/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void addUserFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/user/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void validateTest () throws Exception {
            user.setUsername("tempUserName"); // must change user.username because username column has unique constraint
            mockMvc.perform(post("/user/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void validateTestIfInvalidUser () throws Exception {
            user.setFullname(longString126);
            mockMvc.perform(post("/user/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "User")
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/user/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/user/update/" +
                            (userId))))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsUser(result, user));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/user/update/0")))
                    .andExpect(status().is2xxSuccessful()));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            MvcResult result = mockMvc.perform((get("/user/update/" +
                            (userId))))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, result.getResponse().getContentAsString().contains("Access Denied Exception"));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER", username = "usernameTestValue")
        public void showUpdateFormTestIfLoggedInUser () throws Exception {
            MvcResult result = mockMvc.perform((get("/user/update/" +
                            (userId))))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsUser(result, user));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateUserTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void updateUserTest () throws Exception {
            mockMvc.perform(post("/user/update/" + userId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void updateUserTestIfInvalidUser () throws Exception {
            user.setFullname(longString126);
            mockMvc.perform(post("/user/update/" + userId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void updateUserTestIfNotInDb () throws Exception {
            mockMvc.perform(post("/user/update/0")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateUserTestIfNotAuthorized () throws Exception {
            MvcResult result = mockMvc.perform(post("/user/update/" + userId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, result.getResponse().getContentAsString().contains("Access Denied Exception"));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER", username = "usernameTestValue")
        public void updateUserTestIfLoggedInUser () throws Exception {
            mockMvc.perform(post("/user/update/" + userId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(user.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class DeleteUserTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void deleteUserTest () throws Exception {
            user.setUsername("tempUserName"); // must change user.username because username column has unique constraint
            userRepository.save(user);
            mockMvc.perform(get("/user/delete/" + user.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void deleteUserTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/user/delete/0"))
                    .andExpect(status().is3xxRedirection()));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteUserTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/user/delete/" + userId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
