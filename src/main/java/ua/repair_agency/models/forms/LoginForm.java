package ua.repair_agency.models.forms;

import ua.repair_agency.constants.Parameters;
import ua.repair_agency.services.validation.annotations.MustConform;
import ua.repair_agency.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;

public class LoginForm implements Form {

    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @MustConform(Regex.USER_PASSWORD)
    private final String password;

    public LoginForm(HttpServletRequest req) {
        email = req.getParameter(Parameters.EMAIL);
        password = req.getParameter(Parameters.PASS);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}