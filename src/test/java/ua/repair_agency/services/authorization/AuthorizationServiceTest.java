package ua.repair_agency.services.authorization;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.repair_agency.constants.CRAPaths;
import ua.repair_agency.constants.Role;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.autherization.AuthorizationService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorizationServiceTest {

    private User user;
    private boolean authorized;

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void authorization_unknownPromptAllowedResources_giveTrue(String resources) {
        user = new User.UserBuilder().setRole(Role.UNKNOWN).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertTrue(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.CREATE_ORDER,
            CRAPaths.MANAGER_HOME, CRAPaths.LOGOUT, CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION,
            CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS,
            CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY,
            CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void authorization_unknownPromptNotAllowedResources_giveFalse(String resources) {
        user = new User.UserBuilder().setRole(Role.UNKNOWN).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertFalse(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY,
            CRAPaths.LOGOUT, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void authorization_customerPromptAllowedResources_giveTrue(String resources) {
        user = new User.UserBuilder().setRole(Role.CUSTOMER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertTrue(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION,
            CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS,
            CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY,
            CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGIN, CRAPaths.REGISTRATION,
            CRAPaths.ERROR404, CRAPaths.ERROR500})
    void authorization_customerPromptNotAllowedResources_giveFalse(String resources) {
        user = new User.UserBuilder().setRole(Role.CUSTOMER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertFalse(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void authorization_masterPromptAllowedResources_giveTrue(String resources) {
        user = new User.UserBuilder().setRole(Role.MASTER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertTrue(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION,
            CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER,
            CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGIN, CRAPaths.REGISTRATION,
            CRAPaths.ERROR404, CRAPaths.ERROR500})
    void authorization_masterPromptNotAllowedResources_giveFalse(String resources) {
        user = new User.UserBuilder().setRole(Role.MASTER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertFalse(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void authorization_managerPromptAllowedResources_giveTrue(String resources) {
        user = new User.UserBuilder().setRole(Role.MANAGER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertTrue(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MASTER_HOME, CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION,
            CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER,
            CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS,
            CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void authorization_managerPromptNotAllowedResources_giveFalse(String resources) {
        user = new User.UserBuilder().setRole(Role.MANAGER).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertFalse(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.LOGOUT, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE})
    void authorization_adminPromptAllowedResources_giveTrue(String resources) {
        user = new User.UserBuilder().setRole(Role.ADMIN).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertTrue(authorized);
    }

    @ParameterizedTest
    @ValueSource(strings = {CRAPaths.MANAGER_HOME, CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS,
            CRAPaths.EDIT_STATUS, CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY,
            CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS,
            CRAPaths.MASTERS, CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.ERROR404, CRAPaths.ERROR500})
    void authorization_adminPromptNotAllowedResources_giveFalse(String resources) {
        user = new User.UserBuilder().setRole(Role.ADMIN).build();
        authorized = AuthorizationService.authorize(resources, user);
        assertFalse(authorized);
    }

}
