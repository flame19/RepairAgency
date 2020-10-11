package ua.repair_agency.models.forms;

import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.Parameters;
import ua.repair_agency.constants.Role;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.validation.annotations.*;
import ua.repair_agency.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationForm implements Form {

    @MustConform(Regex.NAMES)
    private final String firstName;
    @MustConform(Regex.NAMES)
    private final String lastName;
    @Email
    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @Pass
    @MustConform(Regex.USER_PASSWORD)
    private final String password;
    @PassConfirmation
    private final String passwordConfirmation;
    @NotEmpty
    private final String language;
    @NotEmpty
    private final Role role;

    public RegistrationForm(HttpServletRequest req) {
        firstName = req.getParameter(Parameters.F_NAME);
        lastName = req.getParameter(Parameters.L_NAME);
        email = req.getParameter(Parameters.EMAIL);
        password = req.getParameter(Parameters.PASS);
        passwordConfirmation = req.getParameter(Parameters.PASS_CONF);
        language = extractLanguage(req);
        role = extractRole(req);
    }

    private String extractLanguage(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        return user.getLanguage();
    }

    private Role extractRole(HttpServletRequest req) {
        String role = req.getParameter(Parameters.ROLE);
        if(role != null){
            return Role.valueOf(role);
        }
        return null;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getLanguage() {
        return language;
    }

    public Role getRole() {
        return role;
    }
}
