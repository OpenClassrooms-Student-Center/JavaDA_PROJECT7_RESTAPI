package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.ErrorController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest(classes = ErrorController.class)
public class ErrorControllerTests extends TestVariables {

    @Autowired
    ErrorController errorController;

    @MockBean
    HttpServletRequest request;

    @Test
    public void ContextLoads() {}

    @Nested
    public class HandleErrorTests {
        @Test
        public void handleErrorTestIf403 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.FORBIDDEN.value());
            assertEquals("403", errorController.handleError(request));
        }
        @Test
        public void handleErrorTestIf404 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.NOT_FOUND.value());
            assertEquals("404", errorController.handleError(request));
        }
        @Test
        public void handleErrorTestIfOtherStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.CONFLICT.value());
            assertEquals("error", errorController.handleError(request));
        }

        @Test
        public void handleErrorTestIfNoStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(null);
            assertEquals("error", errorController.handleError(request));
        }
    }
}
