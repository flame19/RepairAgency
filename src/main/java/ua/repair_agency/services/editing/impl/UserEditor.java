package ua.repair_agency.services.editing.impl;

import ua.repair_agency.models.forms.UserEditingForm;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.UsersDBService;
import ua.repair_agency.services.editing.Editor;

import java.util.LinkedList;
import java.util.List;

public class UserEditor implements Editor {

    private final UserEditingForm form;
    private final User user;
    private final List<UserEdits> edits;

    public UserEditor(UserEditingForm form, User user) {
        this.form = form;
        this.user = user;
        edits = new LinkedList<>();
    }

    public UserEditor compareFirstName() {
        if (!form.getFirstName().equals(user.getFirstName())) {
            edits.add(UserEdits.FIRST_NAME);
        }
        return this;
    }

    public UserEditor compareLastName() {
        if (!form.getLastName().equals(user.getLastName())) {
            edits.add(UserEdits.LAST_NAME);
        }
        return this;
    }

    public UserEditor compareEmail() {
        if (!form.getEmail().equals(user.getEmail())) {
            edits.add(UserEdits.EMAIL);
        }
        return this;
    }

    public UserEditor compareRole() {
        if (!form.getRole().equals(user.getRole())) {
            edits.add(UserEdits.ROLE);
        }
        return this;
    }

    @Override
    public List<UserEdits> getEdits() {
        return edits;
    }

    @Override
    public void edit() {
        UsersDBService.editUser(form, edits);
    }

    public enum UserEdits {
        FIRST_NAME,
        LAST_NAME,
        EMAIL,
        ROLE
    }
}