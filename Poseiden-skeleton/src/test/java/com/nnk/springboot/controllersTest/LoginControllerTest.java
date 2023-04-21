package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)

public class LoginControllerTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private LoginController loginController;




    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(userService, userRepository);

    }
    @Test
    public void getAllUserArticlesTest() {

        List<User> listOfUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(listOfUsers);
        //act
        ModelAndView view = loginController.getAllUserArticles();

        //assert
        assertEquals("user/list", view.getViewName());
    }
    @Test
    public void loginTest(){
        ModelAndView result = loginController.login();
        assertEquals( "login", result.getViewName());

    }
}
