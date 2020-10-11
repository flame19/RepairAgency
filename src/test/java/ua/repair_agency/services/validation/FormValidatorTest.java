package ua.repair_agency.services.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.Parameters;
import ua.repair_agency.models.forms.*;
import ua.repair_agency.models.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FormValidatorTest {

    @Mock
    private HttpServletRequest req;

    private Set<String> inconsistencies = new HashSet<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        inconsistencies.clear();
    }

    @ParameterizedTest
    @CsvSource({"user@mail.com, User1234", "USER@MAIL.com, User1234"})
    void validation_loginFormWithValidData_noInconsistencies(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);
        LoginForm form = new LoginForm(req);
        inconsistencies = FormValidator.validateForm(form);
        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"user@mailcom, User1234", "usermail.com, User1234", "user@mail.c, User1234"})
    void validation_loginFormWithInvalidEmail_oneEmailInconsistency(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);
        LoginForm form = new LoginForm(req);
        inconsistencies = FormValidator.validateForm(form);
        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("email")));

    }

    @ParameterizedTest
    @CsvSource({"user@mail.com, User123", "user@mail.com, user1234", "user@mail.com, ?ser1234"})
    void validation_loginFormWithInvalidPass_onePassInconsistency(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);
        LoginForm form = new LoginForm(req);
        inconsistencies = FormValidator.validateForm(form);
        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("password")));

    }

    @ParameterizedTest
    @CsvSource({"user@mailcom, User123", "usermail.com, user1234", "user@mail.c, ?ser1234", ","})
    void validation_loginFormWithInvalidEmailPass_twoEmailPassInconsistencies(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);
        LoginForm form = new LoginForm(req);
        inconsistencies = FormValidator.validateForm(form);
        assertAll(
                () -> assertEquals(2, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("email")),
                () -> assertTrue(inconsistencies.contains("password")));
    }

    @ParameterizedTest
    @CsvSource({"Firstname, Lastname, user@mail.com, User1234, User1234, en, CUSTOMER",
            "Ім'я, Прізвище, user@mail.com, User1234, User1234, uk, CUSTOMER"})
    void validation_registrationFormWithValidData_noInconsistencies(
            String fName, String lName, String email, String pass, String passConf, String lang, String role) {
        HttpSession session = mock(HttpSession.class);
        User user = mock(User.class);
        when(req.getParameter(Parameters.F_NAME)).thenReturn(fName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(pass);
        when(req.getParameter(Parameters.PASS_CONF)).thenReturn(passConf);
        when(req.getParameter(Parameters.LANG)).thenReturn(lang);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);
        when(user.getLanguage()).thenReturn(lang);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        RegistrationForm form = new RegistrationForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"Firstname+, Lastnaem1, usermail.com, user1234, USer12345, ,",
            "Ім'я%, Прізвище/, user@mail.c, user123, USer12345, ,"})
    void validation_registrationFormWithInvalidData_sevenInconsistencies(
            String fName, String lName, String email, String pass, String passConf, String lang, String role) {
        HttpSession session = mock(HttpSession.class);
        User user = mock(User.class);
        when(req.getParameter(Parameters.F_NAME)).thenReturn(fName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.PASS)).thenReturn(pass);
        when(req.getParameter(Parameters.PASS_CONF)).thenReturn(passConf);
        when(req.getParameter(Parameters.LANG)).thenReturn(lang);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);
        when(user.getLanguage()).thenReturn(lang);
        when(session.getAttribute(Attributes.USER)).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        RegistrationForm form = new RegistrationForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertAll(
                () -> assertEquals(7, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("firstName")),
                () -> assertTrue(inconsistencies.contains("lastName")),
                () -> assertTrue(inconsistencies.contains("email")),
                () -> assertTrue(inconsistencies.contains("password")),
                () -> assertTrue(inconsistencies.contains("passwordConfirmation")),
                () -> assertTrue(inconsistencies.contains("language")),
                () -> assertTrue(inconsistencies.contains("role")));
    }

    @ParameterizedTest
    @CsvSource({
            "Firstname, Lastname, user@mail.com, MANAGER",
            "Ім'я, Прізвище, second-user@mail.com, MASTER"})
    void validation_userEditingFormWithValidData_noInconsistencies(String fName, String lName, String email, String role) {
        when(req.getParameter(Parameters.F_NAME)).thenReturn(fName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);

        UserEditingForm form = new UserEditingForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "Firstname8, Lastname/, usermail.com, ",
            "Ім'я., Прізвище2, second-user@mailcom, "})
    void validation_userEditingFormWithInvalidData_fourInconsistencies(String fName, String lName, String email, String role) {
        when(req.getParameter(Parameters.F_NAME)).thenReturn(fName);
        when(req.getParameter(Parameters.L_NAME)).thenReturn(lName);
        when(req.getParameter(Parameters.EMAIL)).thenReturn(email);
        when(req.getParameter(Parameters.ROLE)).thenReturn(role);

        UserEditingForm form = new UserEditingForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertAll(
                () -> assertEquals(4, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("firstName")),
                () -> assertTrue(inconsistencies.contains("lastName")),
                () -> assertTrue(inconsistencies.contains("email")),
                () -> assertTrue(inconsistencies.contains("role")));
    }

    @ParameterizedTest
    @CsvSource(
            {"Brand-100, 100-model, 2005, ENGINE_REPAIR, Repair description",
                    "Бренд-100, 100-модель, 1995, CHASSIS_REPAIR, Опис ремонту"})
    void validation_orderFormWithValidData_noInconsistencies(
            String brand, String model, String year, String repairType, String repairDescription) {
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter(Parameters.CAR_BRAND)).thenReturn(brand);
        when(req.getParameter(Parameters.CAR_MODEL)).thenReturn(model);
        when(req.getParameter(Parameters.CAR_YEAR)).thenReturn(year);
        when(req.getParameter(Parameters.REPAIR_TYPE)).thenReturn(repairType);

        when(req.getParameter(Parameters.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(session.getAttribute(Attributes.USER)).thenReturn(new User.UserBuilder().build());
        when(req.getSession()).thenReturn(session);

        OrderForm form = new OrderForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "Brand%, , 2100, Repair type, ",
            ", Модель?, 1899, , "})
    void validation_orderFormWithInvalidData_fiveInconsistencies(
            String brand, String model, String year, String repairType, String repairDescription) {
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter(Parameters.CAR_BRAND)).thenReturn(brand);
        when(req.getParameter(Parameters.CAR_MODEL)).thenReturn(model);
        when(req.getParameter(Parameters.CAR_YEAR)).thenReturn(year);
        when(req.getParameter(Parameters.REPAIR_TYPE)).thenReturn(repairType);

        when(req.getParameter(Parameters.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(session.getAttribute(Attributes.USER)).thenReturn(new User.UserBuilder().build());
        when(req.getSession()).thenReturn(session);

        OrderForm form = new OrderForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertAll(
                () -> assertEquals(5, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("carBrand")),
                () -> assertTrue(inconsistencies.contains("carModel")),
                () -> assertTrue(inconsistencies.contains("carYear")),
                () -> assertTrue(inconsistencies.contains("repairType")),
                () -> assertTrue(inconsistencies.contains("repairDescription")));
    }

    @ParameterizedTest
    @CsvSource({"131.25, Some manager comment", "15, Якийсь коментар менеджера"})
    void validation_orderEditingFormWithValidData_noInconsistencies(String price, String managerComment) {
        when(req.getParameter(Parameters.PRICE)).thenReturn(price);
        when(req.getParameter(Parameters.MANAGER_COMMENT)).thenReturn(managerComment);

        OrderEditingForm form = new OrderEditingForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({".25, ", "10.1.3,   "})
    void validation_orderEditingFormWithInvalidData_twoInconsistencies(String price, String managerComment) {
        when(req.getParameter(Parameters.PRICE)).thenReturn(price);
        when(req.getParameter(Parameters.MANAGER_COMMENT)).thenReturn(managerComment);

        OrderEditingForm form = new OrderEditingForm(req);

        Set<String> inconsistencies = FormValidator.validateForm(form);

        assertAll(
                () -> assertEquals(2, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("price")),
                () -> assertTrue(inconsistencies.contains("managerComment")));
    }

    @ParameterizedTest
    @CsvSource({"Some review", "Якийсь відгук"})
    void validation_reviewFormWithValidData_noInconsistencies(String review) {
        when(req.getParameter(Parameters.REVIEW_CONTENT)).thenReturn(review);

        ReviewForm form = new ReviewForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertTrue(inconsistencies.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void validation_reviewFormWithInvalidData_inconsistency(String review) {
        when(req.getParameter(Parameters.REVIEW_CONTENT)).thenReturn(review);

        ReviewForm form = new ReviewForm(req);

        inconsistencies = FormValidator.validateForm(form);

        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains("reviewContent")));
    }
}
