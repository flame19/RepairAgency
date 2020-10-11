package ua.repair_agency.controllers.servlet.commands.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.CRAPaths;
import ua.repair_agency.constants.CRA_JSPFiles;
import ua.repair_agency.controllers.servlet.commands.RequestHandler;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ContentProvideCommandsTest {

    private final Map<String, RequestHandler> COMMANDS = ContentProvideCommands.COMMANDS;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            CRAPaths.LOGIN + ", " + CRA_JSPFiles.LOGIN_MAIN_BLOCK,
            CRAPaths.REGISTRATION + ", " + CRA_JSPFiles.REGISTRATION_MAIN_BLOCK,
            CRAPaths.EDIT_USER + ", " + CRA_JSPFiles.USER_EDITING_MAIN_BLOCK,
            CRAPaths.EDIT_ORDER + ", " + CRA_JSPFiles.ORDER_EDITING_MAIN_BLOCK,
            CRAPaths.CREATE_ORDER + ", " + CRA_JSPFiles.ORDER_FORM,
            CRAPaths.ERROR404 + ", " + CRA_JSPFiles.PAGE404,
            CRAPaths.ERROR500 + ", " + CRA_JSPFiles.PAGE500,
            CRAPaths.MAN_MAS_REGISTRATION + ", " + CRA_JSPFiles.ADMIN_PAGE,
            CRAPaths.HOME + ", " + CRA_JSPFiles.COMMON_HOME,
            CRAPaths.CUSTOMER_HOME + ", " + CRA_JSPFiles.CUSTOMER_MASTER_PAGE,
            CRAPaths.CUSTOMER_ORDER_HISTORY + ", " + CRA_JSPFiles.CUSTOMER_MASTER_PAGE,
            CRAPaths.MANAGER_HOME + ", " + CRA_JSPFiles.MANAGER_PAGE,
            CRAPaths.ACTIVE_ORDERS + ", " + CRA_JSPFiles.MANAGER_PAGE,
            CRAPaths.ORDER_HISTORY + ", " + CRA_JSPFiles.MANAGER_PAGE,
            CRAPaths.CUSTOMERS + ", " + CRA_JSPFiles.MANAGER_PAGE,
            CRAPaths.MASTERS + ", " + CRA_JSPFiles.MANAGER_PAGE,
            CRAPaths.MASTER_HOME + ", " + CRA_JSPFiles.CUSTOMER_MASTER_PAGE,
            CRAPaths.MASTER_COMPLETED_ORDERS + ", " + CRA_JSPFiles.CUSTOMER_MASTER_PAGE,
            CRAPaths.ADMIN_HOME + ", " + CRA_JSPFiles.ADMIN_PAGE,
            CRAPaths.REVIEWS + ", " + CRA_JSPFiles.REVIEWS
    })
    void promptingRecourse_promptConcreteRecourse_dispatchToIt(
            String promptRecourse, String providedSource) throws ServletException, IOException {
        User user = new User.UserBuilder().build();
        when(req.getRequestURI()).thenReturn("http://localhost:8080/RepairAgency/" + promptRecourse);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);
        COMMANDS.get(promptRecourse).handleRequest(req, resp);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, providedSource);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @Test
    void promptingResource_logout_invalidateSessionSendRedirectHome() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        COMMANDS.get(CRAPaths.LOGOUT).handleRequest(req, resp);
        verify(session, times(1)).invalidate();
        verify(resp, times(1)).sendRedirect("null" + CRAPaths.HOME);
    }
}
