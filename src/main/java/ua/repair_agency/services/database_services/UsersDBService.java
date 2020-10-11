package ua.repair_agency.services.database_services;

import ua.repair_agency.constants.Queries;
import ua.repair_agency.constants.ResultTemplate;
import ua.repair_agency.constants.Role;
import ua.repair_agency.exceptions.DataBaseInteractionException;
import ua.repair_agency.models.forms.RegistrationForm;
import ua.repair_agency.models.forms.UserEditingForm;
import ua.repair_agency.models.pagination.PageEntities;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.connection.DBConnectionsPool;
import ua.repair_agency.services.database_services.repository.Repository;
import ua.repair_agency.services.database_services.repository.impl.UniversalRepositoryFactory;
import ua.repair_agency.services.database_services.result_handler.impl.ResultHandlerFactory;
import ua.repair_agency.services.editing.impl.UserEditor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public final class UsersDBService {

    private static final Repository DAO_FACTORY = UniversalRepositoryFactory.getDaoFactory();

    public static void createUser(RegistrationForm registrationForm) {
        LinkedList<Object> formFields = new LinkedList<>();
        extractFormFields(registrationForm, formFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_USER.getQuery(), formFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add user to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractFormFields(RegistrationForm registrationForm, LinkedList<Object> formFields) {
        formFields.add(registrationForm.getFirstName());
        formFields.add(registrationForm.getLastName());
        formFields.add(registrationForm.getEmail());
        formFields.add(registrationForm.getPassword().hashCode());
        formFields.add(registrationForm.getLanguage());
        formFields.add(registrationForm.getRole().name());
    }

    public static User getUserByID(int id) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USER_BY_ID.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), id);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<User> getUsersByRole(Role role) {
        try {
            return (List<User>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_BY_ROLE.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USERS), role.name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<User> getUsersByRoleOffsetAmount(Role role, int offset, int amount) {
        try {
            PageEntities<User> users = new PageEntities<>();
            users.setEntities((List<User>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_BY_ROLE_OFFSET_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USERS),
                    role.name(), offset, amount));
            users.setEntitiesTotalAmount(getUsersAmountByRole(role));
            return users;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getUsersAmountByRole(Role role) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_AMOUNT_BY_ROLE.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT), role.name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static User getUserByEmail(String email) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(), Queries.SELECT_USER_BY_EMAIL.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }

    public static boolean isUserEmailFree(String email) {
        try {
            return (Boolean) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_EMAIL.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.EMAIL), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get email from database because of: " + exc.getMessage(), exc);
        }
    }

    public static void changeUserLanguage(int userId, String language) {
        try (Connection connection = DBConnectionsPool.getConnection()) {
            DAO_FACTORY.update(connection, Queries.UPDATE_USER_LANGUAGE.getQuery(), language, userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't set language to database for user because of: " + exc.getMessage(), exc);
        }
    }

    public static void editUser(UserEditingForm form, List<UserEditor.UserEdits> edits) {

        Connection connection = null;
        try {
            connection = DBConnectionsPool.getConnection();
            connection.setAutoCommit(false);
            for (UserEditor.UserEdits edit : edits) {
                switch (edit) {
                    case FIRST_NAME:
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_FIST_NAME.getQuery(),
                                form.getFirstName(), form.getId());
                        break;
                    case LAST_NAME:
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_LAST_NAME.getQuery(),
                                form.getLastName(), form.getId());
                        break;
                    case EMAIL:
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_EMAIL.getQuery(),
                                form.getEmail(), form.getId());
                        break;
                    case ROLE:
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_ROLE.getQuery(),
                                form.getRole().name(), form.getId());
                        break;
                    default:
                        throw new SQLException("Can't edit such user data: " + edit);
                }
            }
            connection.commit();
        } catch (Throwable exc) {
            try {
                connection.rollback();
            } catch (SQLException rlb_exc) {
                throw new DataBaseInteractionException(
                        "Can't edit user's data and Can't rollback editing because of: " +
                                exc.getMessage() + rlb_exc.getMessage(), rlb_exc);
            }
            throw new DataBaseInteractionException("Can't edit user's data because of: " + exc.getMessage(), exc);
        } finally {
            try {
                connection.close();
            } catch (SQLException cl_exc) {
                throw new DataBaseInteractionException("Can't close connection because of: " + cl_exc.getMessage(), cl_exc);
            }
        }
    }

    public static void deleteUser(int userId) {
        try {
            DAO_FACTORY.delete(DBConnectionsPool.getConnection(), Queries.DELETE_USER_BY_ID.getQuery(), userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't delete user from database because of: " + exc.getMessage(), exc);
        }
    }
}
