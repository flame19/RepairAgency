package ua.repair_agency.controllers.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.CRAPaths;
import ua.repair_agency.constants.Role;
import ua.repair_agency.exceptions.AuthorizationException;
import ua.repair_agency.models.user.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AuthorizationHandleFilterTest {

    @InjectMocks
    private AuthorizationHandleFilter authorizationHandleFilter;

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpSession session;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void checking_authorizedRequestForUNKNOWN_callDoFilterMethod(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.LOGOUT, CRAPaths.ADMIN_HOME,
            CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.MASTER_COMPLETED_ORDERS,
            CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY,
            CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void checking_unauthorizedRequestForUNKNOWN_throwException(String servletPath) {
        User user = new User.UserBuilder().setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        assertThrows(AuthorizationException.class, () -> authorizationHandleFilter.doFilter(req, resp, filterChain));
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.MANAGER_HOME, CRAPaths.MASTER_HOME,})
    void checking_redirectedRequestForUNKNOWN_redirectToLogin(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.UNKNOWN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.LOGIN);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY,
            CRAPaths.LOGOUT, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void checking_authorizedRequestForCustomer_callDoFilterMethod(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.CUSTOMER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS,
            CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS,
            CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void checking_unauthorizedRequestForCUSTOMER_throwException(String servletPath) {
        User user = new User.UserBuilder().setRole(Role.CUSTOMER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        assertThrows(AuthorizationException.class, () -> authorizationHandleFilter.doFilter(req, resp, filterChain));
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.MASTER_HOME,})
    void checking_redirectedRequestForCUSTOMER_redirectToLogin(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.CUSTOMER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.LOGIN);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void checking_authorizedRequestForMASTER_callDoFilterMethod(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.MASTER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGIN, CRAPaths.REGISTRATION,
            CRAPaths.ERROR404, CRAPaths.ERROR500})
    void checking_unauthorizedRequestForMASTER_throwException(String servletPath) {
        User user = new User.UserBuilder().setRole(Role.MASTER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        assertThrows(AuthorizationException.class, () -> authorizationHandleFilter.doFilter(req, resp, filterChain));
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.MANAGER_HOME})
    void checking_redirectedRequestForMASTER_redirectToLogin(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.MASTER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.LOGIN);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void checking_authorizedRequestForMANAGER_callDoFilterMethod(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.MANAGER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER, CRAPaths.DELETE_USER,
            CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS,
            CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void checking_unauthorizedRequestForMANAGER_throwException(String servletPath) {
        User user = new User.UserBuilder().setRole(Role.MANAGER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        assertThrows(AuthorizationException.class, () -> authorizationHandleFilter.doFilter(req, resp, filterChain));
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.MASTER_HOME})
    void checking_redirectedRequestForMANAGER_redirectToLogin(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.MANAGER).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.LOGIN);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.LOGOUT, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void checking_authorizedRequestForADMIN_callDoFilterMethod(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.ADMIN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.CUSTOMER_ORDER_HISTORY,
            CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS,
            CRAPaths.MASTERS, CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void checking_unauthorizedRequestForADMIN_throwException(String servletPath) {
        User user = new User.UserBuilder().setRole(Role.ADMIN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        assertThrows(AuthorizationException.class, () -> authorizationHandleFilter.doFilter(req, resp, filterChain));
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.MASTER_HOME, CRAPaths.MANAGER_HOME})
    void checking_redirectedRequestForADMIN_redirectToLogin(String servletPath) throws IOException, ServletException {
        User user = new User.UserBuilder().setRole(Role.ADMIN).build();
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getServletPath()).thenReturn(servletPath);
        authorizationHandleFilter.doFilter(req, resp, filterChain);
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.LOGIN);
    }
}