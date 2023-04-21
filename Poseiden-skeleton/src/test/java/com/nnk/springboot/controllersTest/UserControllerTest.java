package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.UserController;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")

public class UserControllerTest {

    @Mock
    UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }
    @Test
    public void homeDisplayUserListTest(){

        Model model = new ConcurrentModel();
        List<User> listOfUsers = new ArrayList<>();
        when(userService.findAllUsers()).thenReturn(listOfUsers);
        //act
        String view = userController.homeDisplayUserList(model);

        //assert
        assertEquals("user/list",view );

    }
    @Test
    public void displayAddUserFormTest(){
        User user = new User();
        //act
        String page =  userController.displayAddUserForm(user);
        assertEquals("user/add",page );

    }
    @Test
    public void validateUserTest(){
        User user = new User("firstName", "password00!!", "fullname", "ROLE_ADMIN");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = userController.validateUser(user, result, model);
        //
        assertEquals("redirect:/user/list", page);

    }

    @Test
    public void validateUserWithErrorsOnBindingTest() throws Exception {
        User user = new User("firstName", "password", "fullname", "ROLE_ADMIN");

        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(result.hasErrors()).thenReturn(true);

        String page = userController.validateUser(user, result, model);

        assertEquals("user/add", page);
        //verify(userService, times(0)).registerNewUser(user.getUsername(), user.getPassword(),user.getFullname(), user.getRole());
    }
    @Test
    public void validateUserWithErrorsTest() throws Exception {
        User user = new User("firstName", "password00!!", "fullname", "ROLE_ADMIN");

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        Model model = new ConcurrentModel();

        when(userService.validateNewUser(user)).thenThrow(new Exception());

        String page = userController.validateUser(user, result, model);

        assertEquals("user/add", page);
        //verify(userService, times(0)).registerNewUser(user.getUsername(), user.getPassword(),user.getFullname(), user.getRole());
    }
    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 5;

        when(userService.getById(5)).thenReturn(new User("john", "password","johndoe", "ROLE_ADMIN"));
        String page = userController.displayUpdateUserForm(id, model);

        assertEquals("user/update", page);


    }
    @Test
    public void updateUserTest() throws Exception {
        User user = new User("firstName", "password", "fullname", "ROLE_ADMIN");
        User updatedUser = new User("firstNameUp", "passwordUp", "fullnameUp","ROLE_USER" );
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        Model model = new ConcurrentModel();

        when(userService.updateUser(1, updatedUser)).thenReturn(user);

        String page = userController.validateUser(user, result, model);

        assertEquals("redirect:/user/list", page);

    }
    @Test
    public void updateUserWithErrorTest() throws Exception {
        User user = new User("firstName", "password", "fullname", "ROLE_ADMIN");

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        Model model = new ConcurrentModel();

        when(userService.updateUser(1, user)).thenThrow(new Exception());

        String page = userController.updateUser(1, user, result, model);
        assertEquals("redirect:/user/update/1", page);

    }
    @Test
    public void updateUserWithErrorOnBindindTest() throws Exception {
        User user = new User("firstName", "password", "fullname", "ROLE_ADMIN");

        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(result.hasErrors()).thenReturn(true);
        String page = userController.updateUser(1, user, result, model);
        assertEquals("redirect:/user/update/1", page);

    }
    @Test
    public void deleteUserTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(userService).deleteUser(1);

        userController.deleteUser(1, model);
        verify(userService, times(1)).deleteUser(1);

    }
    @Test
    public void deleteUserWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();

        doThrow(new Exception()).when(userService).deleteUser(1);


        assertEquals("user/list", userController.deleteUser(1, model));
    }

    @Test
    public void encodePasswordTest() {

        String password = "1PasswordForTest?";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode(password);
        Assertions.assertNotEquals("1PasswordForTest?", pw);
        assertTrue(pw.length() > 59);

    }


}
