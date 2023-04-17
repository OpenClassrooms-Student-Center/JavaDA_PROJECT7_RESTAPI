package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        User user = new User("firstName", "fullName", "password", "ROLE_ADMIN");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = userController.validateUser(user, result, model);
        //
        assertEquals("redirect:/user/list", page);

    }
    @Test
    public void validateUserWithErrorsTest() throws Exception {
        User user = new User("firstName", "fullName", "password", "ROLE_ADMIN");

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
    public void displayUserTest(){

    }
    @Test
    public void deleteUserTest(){

    }



}
