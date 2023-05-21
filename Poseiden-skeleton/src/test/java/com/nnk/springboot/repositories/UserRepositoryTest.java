package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    private void setup() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @DisplayName(value = "1°) Recherche de tous les User")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_User() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(userList.size());
    }

    @DisplayName(value = "2°) Recherche d'un User  par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findUser_By_Id() {
        User user = new User(1, "usernameTest", "passwordTest", "fullnameTest", "roleTest");

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Optional<User> userResult = userRepository.findById(user.getId());

        assertThat(userResult).isNotNull();
        assertThat(userResult).isPresent();
        assertThat(userResult.get().getId()).isEqualTo(user.getId());
    }

    @DisplayName(value = "3°) Mise à jour d'un User Existant")
    @Order(3)
    @Test
    void test_update_should_update_User() {
        User user = new User();
        user.setId(1);
        user.setUsername("usernameTest");
        user.setPassword("passwordTest");
        user.setFullname("fullnameTest");
        user.setRole("role");

        User userUpdated = new User();
        user.setId(1);
        user.setUsername("usernameTestUpdated");
        user.setPassword("passwordTestUpdated");
        user.setFullname("fullnameTestUpdated");
        user.setRole("roleTestUpdated");

        when(userRepository.save(any(User.class))).thenReturn(userUpdated);

        User userResult = userRepository.save(user);

        assertThat(userResult).isNotNull();
        assertThat(userResult.getId()).isEqualTo(userUpdated.getId());
        assertThat(userResult.getUsername()).isEqualTo(userUpdated.getUsername());
        assertThat(userResult.getPassword()).isEqualTo(userUpdated.getPassword());
        assertThat(userResult.getFullname()).isEqualTo(userUpdated.getFullname());
        assertThat(userResult.getRole()).isEqualTo(userUpdated.getRole());
    }

    @DisplayName(value = "4°) Suppression d'un User par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteUser_By_Id() {
        User user = new User();
        user.setId(1);
        user.setUsername("usernameTest");
        user.setPassword("passwordTest");
        user.setFullname("fullnameTest");
        user.setRole("role");

        userRepository.deleteById(user.getId());
    }
}
