package ua.repair_agency.services.authentication;

import ua.repair_agency.exceptions.AuthenticationException;
import ua.repair_agency.models.forms.LoginForm;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.UsersDBService;

public final class UserAuthenticator {
    public static User authenticate(LoginForm loginForm) {
        User user = UsersDBService.getUserByEmail(loginForm.getEmail());
        if(user != null){
            if (loginForm.getPassword().hashCode() == user.getPassword()) {
                return user;
            }else {
                throw new AuthenticationException(AuthenticationException.Type.PASS);
            }
        }else {
            throw new AuthenticationException(AuthenticationException.Type.EMAIL);
        }
    }

    private UserAuthenticator() {
    }
}
