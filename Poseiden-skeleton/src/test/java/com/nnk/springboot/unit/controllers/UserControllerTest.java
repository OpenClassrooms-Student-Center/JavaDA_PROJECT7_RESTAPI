package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(secure = false)
public class UserControllerTest {
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        User user = new User();
        user.setRole("role");
        user.setFullname("fullName");
        user.setUsername("userName");
        user.setPassword("password");
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user));
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(user.getFullname()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(user.getUsername()))));
    }
    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        mockMvc.perform(get("/user/add", user))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void testUserValidate() throws Exception {
        User user = new User();
        user.setRole("role");
        user.setFullname("fullName");
        user.setUsername("userName");
        user.setPassword("password");
        Mockito.when(userService.saveOrUpdate(user)).thenReturn(Arrays.asList(user));
        mockMvc.perform(
        post("/user/validate")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(
                "username=username&"+
                "fullname=fullname&"+
                "role=role&"+
                "password=password")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    public void testGetUpdateUser() throws Exception {
        User user = new User();
        user.setRole("role");
        user.setFullname("fullName");
        user.setUsername("userName");
        user.setPassword("password");
        Mockito.when(userService.findById(1)).thenReturn(user);
        mockMvc.perform(get("/user/update/1")

        )
        .andExpect(status().isOk())
        .andExpect(view().name("user/update"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setRole("role");
        user.setFullname("fullName");
        user.setUsername("userName");
        user.setPassword("password");
        Mockito.when(userService.saveOrUpdate(user)).thenReturn(Arrays.asList(user));
        mockMvc.perform(post("/user/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                        "username=username&"+
                                "fullname=fullname&"+
                                "role=role&"+
                                "password=password")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setRole("role");
        user.setFullname("fullName");
        user.setUsername("userName");
        user.setPassword("password");
        Mockito.when(userService.delete(2)).thenReturn(Arrays.asList(user));
        mockMvc.perform(get("/user/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }
}
