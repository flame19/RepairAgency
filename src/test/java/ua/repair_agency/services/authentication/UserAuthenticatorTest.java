package ua.repair_agency.services.authentication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.exceptions.AuthenticationException;
import ua.repair_agency.models.forms.LoginForm;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserAuthenticatorTest {

    @Mock
    private HttpServletRequest req;

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @BeforeEach
    void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource({"testing_customer@mail.com, Testcustomer1", "testing_manager@mail.com, Testmanager1"})
    void authentication_loginFormWithCorrectUserData_giveUser(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);
        User user = UserAuthenticator.authenticate(form);

        assertAll(
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(pass.hashCode(), user.getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"not_existing_customer@mail.com, Testcustomer1", "not_existing_manager@mail.com, Testmanager1"})
    void authentication_loginFormWithWrongEmail_throwAuthenticationException(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);

        assertThrows(AuthenticationException.class, () -> UserAuthenticator.authenticate(form));
    }

    @ParameterizedTest
    @CsvSource({"testing_customer@mail.com, testcustomer", "testing_manager@mail.com, testmanager"})
    void authentication_loginFormWithInvalidPass_throwAuthenticationException(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);

        assertThrows(AuthenticationException.class, () -> UserAuthenticator.authenticate(form));
    }
}
