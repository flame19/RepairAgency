package ua.repair_agency.models.forms;

import ua.repair_agency.constants.Parameters;
import ua.repair_agency.constants.Role;
import ua.repair_agency.services.validation.annotations.Email;
import ua.repair_agency.services.validation.annotations.MustConform;
import ua.repair_agency.services.validation.annotations.NotEmpty;
import ua.repair_agency.services.validation.annotations.UserID;
import ua.repair_agency.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;

public class UserEditingForm implements Form {

    @UserID
    private final int id;
    @MustConform(Regex.NAMES)
    private final String firstName;
    @MustConform(Regex.NAMES)
    private final String lastName;
    @Email
    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @NotEmpty
    private final Role role;

    public UserEditingForm(HttpServletRequest req) {
        id = extractId(req);
        firstName = req.getParameter(Parameters.F_NAME);
        lastName = req.getParameter(Parameters.L_NAME);
        email = req.getParameter(Parameters.EMAIL);
        role = extractRole(req);
    }

    private int extractId(HttpServletRequest req) {
        String id = req.getParameter(Parameters.EDITING_USER_ID);
        if(id != null){
            return Integer.parseInt(id);
        }
        return 0;
    }

    private Role extractRole(HttpServletRequest req) {
        String role = req.getParameter(Parameters.ROLE);
        if(role != null){
            return Role.valueOf(role);
        }
        return null;
    }

    public int getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
