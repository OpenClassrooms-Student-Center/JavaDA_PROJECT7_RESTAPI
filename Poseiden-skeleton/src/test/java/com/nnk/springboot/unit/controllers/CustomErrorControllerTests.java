package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.CustomErrorController;
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
@SpringBootTest(classes = CustomErrorController.class)
public class CustomErrorControllerTests extends TestVariables {

    @Autowired
    CustomErrorController customErrorController;

    @MockBean
    HttpServletRequest request;

    @Test
    public void ContextLoads() {}

    @Nested
    public class HandleErrorTests {
        @Test
        public void handleErrorTestIf403 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.FORBIDDEN.value());
            assertEquals("403", customErrorController.handleError(request));
        }
        @Test
        public void handleErrorTestIf404 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.NOT_FOUND.value());
            assertEquals("404", customErrorController.handleError(request));
        }
        @Test
        public void handleErrorTestIfOtherStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.CONFLICT.value());
            assertEquals("error", customErrorController.handleError(request));
        }

        @Test
        public void handleErrorTestIfNoStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(null);
            assertEquals("error", customErrorController.handleError(request));
        }
    }
}
