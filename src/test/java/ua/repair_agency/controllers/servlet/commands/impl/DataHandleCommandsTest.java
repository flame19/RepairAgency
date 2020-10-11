package ua.repair_agency.controllers.servlet.commands.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.*;
import ua.repair_agency.controllers.servlet.commands.RequestHandler;
import ua.repair_agency.exceptions.AuthenticationException;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataHandleCommandsTest {

    @InjectMocks
    private final Map<String, RequestHandler> COMMANDS = DataHandleCommands.COMMANDS;

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
            "testing_admin@mail.com, Admin123," + CRAPaths.ADMIN_HOME,
            "testing_customer@mail.com, Testcustomer1," + CRAPaths.CUSTOMER_HOME,
            "testing_manager@mail.com, Testmanager1," + CRAPaths.MANAGER_HOME,
            "testing_master@mail.com, Testmaster1," + CRAPaths.MASTER_HOME})
    void promptingLogin_correctData_setAppropriateUserToSessionAndRedirectToUserHomePage(String email, String password, String userHome) throws ServletException, IOException {

        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(password);
        when(req.getSession()).thenReturn(session);

        COMMANDS.get(CRAPaths.LOGIN).handleRequest(req, resp);

        verify(session, times(1)).setAttribute(any(), any());
        verify(resp, times(1)).sendRedirect("null" + userHome);
    }


    @ParameterizedTest
    @CsvSource({
            "testing_admin@mail.com, admin,",
            "testing_customer@mail.com, customer1,",
            "testing_manager@mail.com, manager1,",
            "testing_master@mail.com, master1,"})
    void promptingLogin_incorrectPass_dispatchToLoginPageAndThrowException(String email, String password) throws ServletException, IOException {

        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(password);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        assertThrows(AuthenticationException.class, () -> COMMANDS.get(CRAPaths.LOGIN).handleRequest(req, resp));

        verify(resp, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.LOGIN_MAIN_BLOCK);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource({
            "admin@mail.com, Admin123,",
            "customer@mail.com, Testcustomer1,",
            "manager@mail.com, Testmanager1,",
            "master@mail.com, Testmaster1,"})
    void promptingLogin_incorrectEmail_dispatchToLoginPageAndThrowException(String email, String password) throws ServletException, IOException {

        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(password);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        assertThrows(AuthenticationException.class, () -> COMMANDS.get(CRAPaths.LOGIN).handleRequest(req, resp));

        verify(resp, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.LOGIN_MAIN_BLOCK);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource(" , , registration_testingmail.com, User123, User12345")
    void promptingRegistration_invalidData_setBadRequestDispatchToRegistration(
            String firstName, String lastName, String email,
            String password, String passwordConfirmation) throws ServletException, IOException {

        when(req.getParameter(Parameters.F_NAME)).thenReturn(firstName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lastName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(password);
        when(req.getParameter(Parameters.PASS_CONF)).thenReturn(passwordConfirmation);
        when(req.getParameter(Parameters.ROLE)).thenReturn(Role.CUSTOMER.name());
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(
                new User.UserBuilder().setLanguage(CommonConstants.EN).build());
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.REGISTRATION).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REGISTRATION_MAIN_BLOCK);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource(" , , registration_testing_managermail.com, User123, User12345, MANAGER")
    void promptingManMasRegistration_invalidData_setBadRequestDispatchToRegistration(
            String firstName, String lastName, String email,
            String password, String passwordConfirmation, String role) throws ServletException, IOException {

        when(req.getParameter(Parameters.F_NAME)).thenReturn(firstName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lastName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(password);
        when(req.getParameter(Parameters.PASS_CONF)).thenReturn(passwordConfirmation);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(
                new User.UserBuilder().setLanguage(CommonConstants.EN).build());
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.MAN_MAS_REGISTRATION).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ADMIN_PAGE);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource("CarBrand, CarModel-100, 2015, ENGINE_REPAIR, Need fast and not expensive repairing")
    void promptingCreateOrder_validData_createOrderDispatchToCreateOrder(
            String carBrand, String carModel, String year,
            String repairType, String repairDescription) throws ServletException, IOException {

        when(req.getParameter(Parameters.CAR_BRAND)).thenReturn(carBrand);
        when(req.getParameter(Parameters.CAR_MODEL)).thenReturn(carModel);
        when(req.getParameter(Parameters.CAR_YEAR)).thenReturn(year);
        when(req.getParameter(Parameters.REPAIR_TYPE)).thenReturn(repairType);
        when(req.getParameter(Parameters.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(
                new User.UserBuilder().setId(2).build());
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.CREATE_ORDER).handleRequest(req, resp);

        verifyNoInteractions(resp);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_FORM);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource("CarBrand, , 2015, ENGINE_REPAIR, ")
    void promptingCreateOrder_invalidData_setBadRequestDispatchToCreateOrder(
            String carBrand, String carModel, String year,
            String repairType, String repairDescription) throws ServletException, IOException {

        when(req.getParameter(Parameters.CAR_BRAND)).thenReturn(carBrand);
        when(req.getParameter(Parameters.CAR_MODEL)).thenReturn(carModel);
        when(req.getParameter(Parameters.CAR_YEAR)).thenReturn(year);
        when(req.getParameter(Parameters.REPAIR_TYPE)).thenReturn(repairType);
        when(req.getParameter(Parameters.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.USER)).thenReturn(
                new User.UserBuilder().setLanguage(CommonConstants.EN).build());
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.CREATE_ORDER).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_FORM);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @Test
    void promptingDeleteUser_deleteUserFromDatabaseAndRedirectAdminHome() throws ServletException, IOException {

        when(req.getParameter(Parameters.DELETING_USER_ID)).thenReturn("0");

        COMMANDS.get(CRAPaths.DELETE_USER).handleRequest(req, resp);

        verify(resp).sendRedirect("null" + CRAPaths.ADMIN_HOME);
    }

    @ParameterizedTest
    @CsvSource("5, Edited user, Edited, edited_master@mail.com, MASTER")
    void promptingEditUser_validData_editUserRedirectToAdminHome(
            String userID, String firstName, String lastName,
            String email, String role) throws ServletException, IOException {

        when(req.getParameter(Parameters.EDITING_USER_ID)).thenReturn(userID);
        when(req.getParameter(Parameters.F_NAME)).thenReturn(firstName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lastName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);

        COMMANDS.get(CRAPaths.EDIT_USER).handleRequest(req, resp);

        verify(resp).sendRedirect("null" + CRAPaths.ADMIN_HOME);
    }

    @ParameterizedTest
    @CsvSource("5, , , testing_master@mail.com, MASTER")
    void promptingEditUser_invalidData_setBadRequestDispatchToEditUser(
            String userID, String firstName, String lastName,
            String email, String role) throws ServletException, IOException {

        when(req.getParameter(Parameters.EDITING_USER_ID)).thenReturn(userID);
        when(req.getParameter(Parameters.F_NAME)).thenReturn(firstName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lastName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.EDIT_USER).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.USER_EDITING_MAIN_BLOCK);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource("1, 100.25, 3, CAR_WAITING, Some manager comment")
    void promptingEditOrder_validData_editOrderRedirectToManagerHome(
            String orderID, String price, String masterID,
            String status, String managerComment) throws ServletException, IOException {

        when(req.getParameter(Parameters.EDITING_ORDER_ID)).thenReturn(orderID);
        when(req.getParameter(Parameters.PRICE)).thenReturn(price);
        when(req.getParameter(Parameters.MASTER_ID)).thenReturn(masterID);
        when(req.getParameter(Parameters.STATUS)).thenReturn(status);
        when(req.getParameter(Parameters.MANAGER_COMMENT)).thenReturn(managerComment);

        COMMANDS.get(CRAPaths.EDIT_ORDER).handleRequest(req, resp);

        verify(resp).sendRedirect("null" + CRAPaths.MANAGER_HOME);
    }

    @ParameterizedTest
    @CsvSource("1, 100.25a, 0, CAR_WAITING, ")
    void promptingEditOrder_invalidData_setBadRequestDispatchToEditUser(
            String orderID, String price, String masterID,
            String status, String managerComment) throws ServletException, IOException {

        when(req.getParameter(Parameters.EDITING_ORDER_ID)).thenReturn(orderID);
        when(req.getParameter(Parameters.PRICE)).thenReturn(price);
        when(req.getParameter(Parameters.MASTER_ID)).thenReturn(masterID);
        when(req.getParameter(Parameters.STATUS)).thenReturn(status);
        when(req.getParameter(Parameters.MANAGER_COMMENT)).thenReturn(managerComment);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.EDIT_ORDER).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_EDITING_MAIN_BLOCK);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource({"1, REPAIR_WORK", "1, REPAIR_COMPLETED"})
    void promptingEditStatus_editOrderStatusAndRedirectToManagerHome(
            String orderID, String status) throws ServletException, IOException {

        when(req.getParameter(Parameters.STATUS)).thenReturn(status);
        when(req.getParameter(Parameters.ORDER_ID)).thenReturn(orderID);

        COMMANDS.get(CRAPaths.EDIT_STATUS).handleRequest(req, resp);

        verify(resp).sendRedirect("null" + CRAPaths.MASTER_HOME);
    }


    @ParameterizedTest
    @CsvSource("2, Some customer review, http://localhost:8080/RepairAgency/reviews")
    void promptingReviews_validData_createReviewInDatabaseDispatchToReviews(
            String customerID, String reviewContent, String requestUri) throws ServletException, IOException {

        when(req.getRequestURI()).thenReturn(requestUri);
        when(req.getParameter(Parameters.CUSTOMER_ID)).thenReturn(customerID);
        when(req.getParameter(Parameters.REVIEW_CONTENT)).thenReturn(reviewContent);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.REVIEWS).handleRequest(req, resp);

        verifyNoInteractions(resp);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REVIEWS);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @ParameterizedTest
    @CsvSource("2, , http://localhost:8080/RepairAgency/reviews")
    void promptingReviews_invalidData_setBadRequestAndDispatchToReviews(
            String customerID, String reviewContent, String requestUri) throws ServletException, IOException {

        when(req.getRequestURI()).thenReturn(requestUri);
        when(req.getParameter(Parameters.CUSTOMER_ID)).thenReturn(customerID);
        when(req.getParameter(Parameters.REVIEW_CONTENT)).thenReturn(reviewContent);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);

        COMMANDS.get(CRAPaths.REVIEWS).handleRequest(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(req, times(1)).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REVIEWS);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }
}
