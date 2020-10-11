package ua.repair_agency.controllers.filters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.CommonConstants;
import ua.repair_agency.constants.Parameters;
import ua.repair_agency.constants.Role;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LanguageHandleFilterTest {

    @InjectMocks
    private LanguageHandleFilter languageHandleFilter;

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpSession session;

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void settingEnLanguage_forUNKNOWN_setUserSessionLanguage() throws IOException, ServletException {
        User user = new User.UserBuilder().setId(0).setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(Parameters.LANG)).thenReturn(CommonConstants.EN);
        when(req.getParameter(Parameters.PREV_URL)).thenReturn("/home");
        when(req.getParameter(Parameters.PAGE)).thenReturn(null);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        doNothing().when(resp).addCookie(any());

        languageHandleFilter.doFilter(req, resp, filterChain);

        assertEquals(CommonConstants.EN, user.getLanguage());
    }

    @Test
    void settingEnUkLanguage_forCUTOMER_setUserSessionAndDBLanguage() throws IOException, ServletException {
        User user = new User.UserBuilder().setId(2).setRole(Role.CUSTOMER).build();
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(Parameters.LANG)).thenReturn("en");
        when(req.getParameter(Parameters.PREV_URL)).thenReturn("/home");
        when(req.getParameter(Parameters.PAGE)).thenReturn(null);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        doNothing().when(resp).addCookie(any());

        languageHandleFilter.doFilter(req, resp, filterChain);

        assertEquals("en", user.getLanguage());

        when(req.getParameter(Parameters.LANG)).thenReturn("uk");

        languageHandleFilter.doFilter(req, resp, filterChain);

        assertEquals("uk", user.getLanguage());
    }

    @Test
    void settingLanguage_fromPage_sendRedirectThisPage() throws IOException, ServletException {
        User user = new User.UserBuilder().setId(0).setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(Parameters.LANG)).thenReturn(any());
        when(req.getParameter(Parameters.PREV_URL)).thenReturn("/home");
        when(req.getParameter(Parameters.PAGE)).thenReturn(null);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        doNothing().when(resp).addCookie(any());

        languageHandleFilter.doFilter(req, resp, filterChain);

        verify(resp).sendRedirect("/home");
    }

    @Test
    void settingLanguage_fromNumberedPage_sendRedirectToThatPage() throws IOException, ServletException {
        User user = new User.UserBuilder().setId(0).setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(Parameters.LANG)).thenReturn(any());
        when(req.getParameter(Parameters.PREV_URL)).thenReturn("/home");
        when(req.getParameter(Parameters.PAGE)).thenReturn("7");
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        doNothing().when(resp).addCookie(any());

        languageHandleFilter.doFilter(req, resp, filterChain);

        verify(resp).sendRedirect("/home" + CommonConstants.PAGE_EQUAL + "7");
    }
}
