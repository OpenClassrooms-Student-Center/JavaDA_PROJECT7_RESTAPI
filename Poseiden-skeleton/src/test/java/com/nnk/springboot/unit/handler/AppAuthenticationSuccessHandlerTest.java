package com.nnk.springboot.unit.handler;

import com.nnk.springboot.handler.AppAuthenticationSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AppAuthenticationSuccessHandlerTest {

    RedirectStrategy redirectStrategy;
    AppAuthenticationSuccessHandler appAuthenticationSuccessHandler;

    @BeforeEach
    public void init() {
        redirectStrategy = Mockito.mock(RedirectStrategy.class);
        appAuthenticationSuccessHandler = new AppAuthenticationSuccessHandler();
        ReflectionTestUtils.setField(appAuthenticationSuccessHandler,"redirectStrategy", redirectStrategy);
    }

    @Test
    public void shouldOnAuthenticationSuccessRedirectHome() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        appAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse,authentication);
        verify(redirectStrategy, times(1)).sendRedirect(httpServletRequest, httpServletResponse, "/");

    }

}
