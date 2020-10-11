package ua.repair_agency.services.database_services.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.repair_agency.constants.Queries;
import ua.repair_agency.constants.ResultTemplate;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.connection.DBConnectionsPool;
import ua.repair_agency.services.database_services.result_handler.impl.ResultHandlerFactory;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UniversalDAOFactoryTest {

    private static UniversalRepositoryFactory daoFactory = UniversalRepositoryFactory.getDaoFactory();

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
        daoFactory = UniversalRepositoryFactory.getDaoFactory();
    }

    @ParameterizedTest
    @CsvSource("insertusername, insertuserlastname, insert_user@mail.com, 12345678, en, CUSTOMER")
    void inserting_insertToDataBase(String firstName, String lastName,
                                    String email, String password, String lang, String role) {
        assertDoesNotThrow(() -> daoFactory.insert(
                DBConnectionsPool.getConnection(),
                Queries.INSERT_USER.getQuery(),
                firstName, lastName, email, password, lang, role));
    }

    @ParameterizedTest
    @CsvSource({
            "testing_admin@mail.com, 1, Admin, testing_admin@mail.com",
            "testing_customer@mail.com, 2, Customer, testing_customer@mail.com",
            "testing_master@mail.com, 3, Master, testing_master@mail.com",
            "testing_manager@mail.com, 4, Manager, testing_manager@mail.com"})
    void select_userFromDataBase_giveUser(
            String email, int id, String lastName, String dbUserEmail) throws SQLException {
        User user = (User) daoFactory.select(
                DBConnectionsPool.getConnection(),
                Queries.SELECT_USER_BY_EMAIL.getQuery(),
                ResultHandlerFactory.HANDLER.get(ResultTemplate.USER),
                email);
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(id, user.getId()),
                () -> assertEquals(lastName, user.getLastName()),
                () -> assertEquals(dbUserEmail, user.getEmail()));
    }

    @ParameterizedTest
    @CsvSource("userfordeleting, userfordeleting, deletign_user@mail.com, 12345678, en, CUSTOMER")
    void deleting_deleteFromDataBase(String firstName, String lastName,
                                     String email, String password, String lang, String role) throws SQLException {
        daoFactory.insert(DBConnectionsPool.getConnection(),
                Queries.INSERT_USER.getQuery(),
                firstName, lastName, email, password, lang, role);

        assertDoesNotThrow(() -> daoFactory.delete(DBConnectionsPool.getConnection(),
                Queries.DELETE_USER_BY_EMAIL.getQuery(), email));
    }

    @ParameterizedTest
    @CsvSource("uk, 5")
    void updating_updateDataInDataBase(String language, String userId) {
        assertDoesNotThrow(() -> daoFactory.update(
                DBConnectionsPool.getConnection(),
                Queries.UPDATE_USER_LANGUAGE.getQuery(),
                language, userId));
    }
}
