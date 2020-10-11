package ua.repair_agency.services.editing.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.Role;
import ua.repair_agency.models.forms.UserEditingForm;
import ua.repair_agency.models.user.User;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserEditorTest {

    private UserEditor editor;
    private List<UserEditor.UserEdits> edits = new LinkedList<>();

    @Mock
    UserEditingForm editingForm;

    @Mock
    User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        editor = new UserEditor(editingForm, user);
        edits.clear();
    }

    @ParameterizedTest
    @CsvSource({"Username, Other username", "Ім'я, Інше ім'я"})
    void comparing_differentFirstNames_giveOneFirstNameEdit(String formFirstName, String userFirstName) {
        when(editingForm.getFirstName()).thenReturn(formFirstName);
        when(user.getFirstName()).thenReturn(userFirstName);
        editor = editor.compareFirstName();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.FIRST_NAME)));
    }

    @ParameterizedTest
    @CsvSource({"Username, Username", "Ім'я, Ім'я"})
    void comparing_sameFirstNames_giveNoEdit(String formFirstName, String userFirstName) {
        when(editingForm.getFirstName()).thenReturn(formFirstName);
        when(user.getFirstName()).thenReturn(userFirstName);
        editor = editor.compareFirstName();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"Userlastname, Other userlastname", "Прізвище, Інше прізвище"})
    void comparing_differentLastNames_giveOneLastNameEdit(String formLastName, String userLastName) {
        when(editingForm.getLastName()).thenReturn(formLastName);
        when(user.getLastName()).thenReturn(userLastName);
        editor = editor.compareLastName();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.LAST_NAME)));
    }

    @ParameterizedTest
    @CsvSource({"Userlastname, Userlastname", "Прізвище, Прізвище"})
    void comparing_sameLastNames_giveNoEdit(String formLastName, String userLastName) {
        when(editingForm.getLastName()).thenReturn(formLastName);
        when(user.getLastName()).thenReturn(userLastName);
        editor = editor.compareLastName();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"user@mail.com, other@mail.com"})
    void comparing_differentEmails_giveOneEmailEdit(String formEmail, String userEmail) {
        when(editingForm.getEmail()).thenReturn(formEmail);
        when(user.getEmail()).thenReturn(userEmail);
        editor = editor.compareEmail();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.EMAIL)));
    }

    @ParameterizedTest
    @CsvSource({"user@mail.com, user@mail.com"})
    void comparing_sameEmails_giveNoEdit(String formEmail, String userEmail) {
        when(editingForm.getEmail()).thenReturn(formEmail);
        when(user.getEmail()).thenReturn(userEmail);
        editor = editor.compareEmail();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"MANAGER, MASTER"})
    void comparing_differentRoles_giveOneRoleEdit(String formRole, String userRole) {
        when(editingForm.getRole()).thenReturn(Role.valueOf(formRole));
        when(user.getRole()).thenReturn(Role.valueOf(userRole));
        editor = editor.compareRole();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.ROLE)));
    }

    @ParameterizedTest
    @CsvSource({"MANAGER, MANAGER"})
    void comparing_sameRoles_giveNoEdit(String formRole, String userRole) {
        when(editingForm.getRole()).thenReturn(Role.valueOf(formRole));
        when(user.getRole()).thenReturn(Role.valueOf(userRole));
        editor = editor.compareRole();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"Username, Userlastname, user@mail.com, MANAGER, " +
            "Other Username, Other userlastname, other@mail.com, MASTER"})
    void comparing_differentData_giveFourEdits(
            String formFirstName, String formLastName, String formEmail, String formRole,
            String userFirstName, String userLastName, String userEmail, String userRole) {
        when(editingForm.getFirstName()).thenReturn(formFirstName);
        when(user.getFirstName()).thenReturn(userFirstName);
        when(editingForm.getLastName()).thenReturn(formLastName);
        when(user.getLastName()).thenReturn(userLastName);
        when(editingForm.getEmail()).thenReturn(formEmail);
        when(user.getEmail()).thenReturn(userEmail);
        when(editingForm.getRole()).thenReturn(Role.valueOf(formRole));
        when(user.getRole()).thenReturn(Role.valueOf(userRole));
        editor = editor.compareFirstName().compareLastName().compareEmail().compareRole();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(4, edits.size()),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.FIRST_NAME)),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.LAST_NAME)),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.EMAIL)),
                () -> assertTrue(edits.contains(UserEditor.UserEdits.ROLE)));
    }

    @ParameterizedTest
    @CsvSource({"Username, Userlastname, user@mail.com, MANAGER, " +
            "Username, Userlastname, user@mail.com, MANAGER"})
    void comparing_sameData_giveNoEdits(
            String formFirstName, String formLastName, String formEmail, String formRole,
            String userFirstName, String userLastName, String userEmail, String userRole) {
        when(editingForm.getFirstName()).thenReturn(formFirstName);
        when(user.getFirstName()).thenReturn(userFirstName);
        when(editingForm.getLastName()).thenReturn(formLastName);
        when(user.getLastName()).thenReturn(userLastName);
        when(editingForm.getEmail()).thenReturn(formEmail);
        when(user.getEmail()).thenReturn(userEmail);
        when(editingForm.getRole()).thenReturn(Role.valueOf(formRole));
        when(user.getRole()).thenReturn(Role.valueOf(userRole));
        editor = editor.compareFirstName().compareLastName().compareEmail().compareRole();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }
}
