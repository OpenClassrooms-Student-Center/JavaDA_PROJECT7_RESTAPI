package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<User> user = Arrays.asList(new User(), new User(), new User());
        when(userService.findAll()).thenReturn(user);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", user));

        verify(userService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        User user = new User();
        //user.setId(22);
        user.setFullname("B");
        user.setUsername("A");
        user.setPassword("12345678Az!");
        user.setRole("USER");

        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("fullname", user.getFullname())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("role", user.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).create(any(User.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        User user = new User();

        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("fullname", user.getFullname())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("role", user.getRole()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("user", "fullname", "username", "password","role"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.findById(1)).thenReturn(user);

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(userService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int userId = 1;
        User user = new User();
        user.setFullname("B");
        user.setUsername("A");
        user.setPassword("12345678Az!");
        user.setRole("USER");

        given(userService.findById(userId)).willReturn(user);

        mockMvc.perform(get("/user/update/" + userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int id = 1;
        User user = new User();
        user.setFullname("B");
        user.setUsername("A");
        user.setPassword("12345678Az!");
        user.setRole("USER");

        given(userService.update(eq(id), any(User.class))).willReturn(user);

        mockMvc.perform(post("/user/update/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullname", user.getFullname())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("role", user.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int id = 1;

        mockMvc.perform(get("/user/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors());

        verify(userService).delete(id);
    }
}
