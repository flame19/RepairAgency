package ua.repair_agency.services.database_services.result_handler.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.*;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.models.review.Review;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.result_handler.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ResultHandlerFactoryTest {

    private static Map<ResultTemplate, ResultHandler<?>> HANDLER;

    @Mock
    private ResultSet resultSet;

    @BeforeAll
    static void initHandler(){
        HANDLER = ResultHandlerFactory.HANDLER;
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource(" 5, Username, Userlastname, user@mail.com, 12345678, en, CUSTOMER")
    void handleResultSet_forUserPattern_returnUser(
            int userID, String firstName, String lastName,
            String email, int password, String language, String role) throws SQLException {

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(DBColumns.ID)).thenReturn(userID);
        when(resultSet.getString(DBColumns.FIRST_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.LAST_NAME)).thenReturn(lastName);
        when(resultSet.getString(DBColumns.EMAIL)).thenReturn(email);
        when(resultSet.getInt(DBColumns.PASSWORD)).thenReturn(password);
        when(resultSet.getString(DBColumns.LANGUAGE)).thenReturn(language);
        when(resultSet.getString(DBColumns.ROLE)).thenReturn(role);

        User user = (User) HANDLER.get(ResultTemplate.USER).handleResultSet(resultSet);

        assertAll(
                () -> assertEquals(userID, user.getId()),
                () -> assertEquals(firstName, user.getFirstName()),
                () -> assertEquals(lastName, user.getLastName()),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(password, user.getPassword()),
                () -> assertEquals(language, user.getLanguage()),
                () -> assertEquals(Role.valueOf(role), user.getRole()));
    }

    @ParameterizedTest
    @CsvSource(" 5, Username, Userlastname, user@mail.com, 12345678, en, CUSTOMER")
    void handleResultSet_forUsersPattern_returnUsersList(
            int userID, String firstName, String lastName,
            String email, int password, String language, String role) throws SQLException {

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(DBColumns.ID)).thenReturn(userID);
        when(resultSet.getString(DBColumns.FIRST_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.LAST_NAME)).thenReturn(lastName);
        when(resultSet.getString(DBColumns.EMAIL)).thenReturn(email);
        when(resultSet.getInt(DBColumns.PASSWORD)).thenReturn(password);
        when(resultSet.getString(DBColumns.LANGUAGE)).thenReturn(language);
        when(resultSet.getString(DBColumns.ROLE)).thenReturn(role);

        List<User> usersList = (List<User>) HANDLER.get(ResultTemplate.USERS).handleResultSet(resultSet);

        assertEquals( 3, usersList.size());
    }

    @ParameterizedTest
    @CsvSource(" 5, Username, Userlastname, user@mail.com," +
            "CarBrand, CarModel, 2018, BATTERY_REPLACEMENT," +
            "Some repair description, 40, 10," +
            "MasterName, MasterLastName, ORDER_COMPLETED, Some manager comment")
    void handleResultSet_forOrderPattern_returnOrder(
            int customerID, String firstName, String lastName, String email,
            String brand, String model, String year, String repairType,
            String repairDescription, double price, int masterID,
            String masterFirstName, String masterLastName, String orderStatus,
            String managerComment) throws SQLException {

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(DBColumns.CUSTOMER_ID)).thenReturn(customerID);
        when(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.CUSTOMER_L_NAME)).thenReturn(lastName);
        when(resultSet.getString(DBColumns.EMAIL)).thenReturn(email);
        when(resultSet.getString(DBColumns.BRAND)).thenReturn(brand);
        when(resultSet.getString(DBColumns.MODEL)).thenReturn(model);
        when(resultSet.getString(DBColumns.YEAR)).thenReturn(year);
        when(resultSet.getString(DBColumns.REPAIR_TYPE)).thenReturn(repairType);
        when(resultSet.getString(DBColumns.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(resultSet.getDouble(DBColumns.PRICE)).thenReturn(price);
        when(resultSet.getInt(DBColumns.MASTER_ID)).thenReturn(masterID);
        when(resultSet.getString(DBColumns.MASTER_F_NAME)).thenReturn(masterFirstName);
        when(resultSet.getString(DBColumns.MASTER_L_NAME)).thenReturn(masterLastName);
        when(resultSet.getString(DBColumns.STATUS)).thenReturn(orderStatus);
        when(resultSet.getString(DBColumns.MANAGER_COMMENT)).thenReturn(managerComment);

        LocalDateTime dateTime = LocalDateTime.now();
        when(resultSet.getTimestamp(DBColumns.CREATION_DATE)).thenReturn(Timestamp.valueOf(dateTime));
        when(resultSet.getTimestamp(DBColumns.REPAIR_COMPLETION_DATE)).
                thenReturn(Timestamp.valueOf(dateTime.plus(90, ChronoUnit.MINUTES)));

        Order order = (Order) HANDLER.get(ResultTemplate.ORDER).handleResultSet(resultSet);
        User customerFromOrder = order.getCustomer();
        User masterFromOrder = order.getMaster();

        assertAll(
                () -> assertEquals(customerID, customerFromOrder.getId()),
                () -> assertEquals(firstName, customerFromOrder.getFirstName()),
                () -> assertEquals(lastName, customerFromOrder.getLastName()),
                () -> assertEquals(email, customerFromOrder.getEmail()),
                () -> assertEquals(brand, order.getCarBrand()),
                () -> assertEquals(model, order.getCarModel()),
                () -> assertEquals(year, order.getCarYearManufacture()),
                () -> assertEquals(RepairType.valueOf(repairType), order.getRepairType()),
                () -> assertEquals(repairDescription, order.getRepairDescription()),
                () -> assertEquals(price, order.getPrice()),
                () -> assertEquals(masterID, masterFromOrder.getId()),
                () -> assertEquals(masterFirstName, masterFromOrder.getFirstName()),
                () -> assertEquals(masterLastName, masterFromOrder.getLastName()),
                () -> assertEquals(OrderStatus.valueOf(orderStatus), order.getStatus()),
                () -> assertEquals(managerComment, order.getManagerComment()),
                () -> assertEquals(dateTime, order.getDate()),
                () -> assertEquals(dateTime.plus(90, ChronoUnit.MINUTES), order.getRepairCompletionDate()));
    }

    @ParameterizedTest
    @CsvSource(" 5, Username, Userlastname, user@mail.com," +
            "CarBrand, CarModel, 2018, BATTERY_REPLACEMENT," +
            "Some repair description, 40, 10," +
            "MasterName, MasterLastName, ORDER_COMPLETED, Some manager comment")
    void handleResultSet_forOrdersPattern_returnOrders(
            int customerID, String firstName, String lastName, String email,
            String brand, String model, String year, String repairType,
            String repairDescription, double price, int masterID,
            String masterFirstName, String masterLastName, String orderStatus,
            String managerComment) throws SQLException {

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(DBColumns.CUSTOMER_ID)).thenReturn(customerID);
        when(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.CUSTOMER_L_NAME)).thenReturn(lastName);
        when(resultSet.getString(DBColumns.EMAIL)).thenReturn(email);
        when(resultSet.getString(DBColumns.BRAND)).thenReturn(brand);
        when(resultSet.getString(DBColumns.MODEL)).thenReturn(model);
        when(resultSet.getString(DBColumns.YEAR)).thenReturn(year);
        when(resultSet.getString(DBColumns.REPAIR_TYPE)).thenReturn(repairType);
        when(resultSet.getString(DBColumns.REPAIR_DESCRIPTION)).thenReturn(repairDescription);
        when(resultSet.getDouble(DBColumns.PRICE)).thenReturn(price);
        when(resultSet.getInt(DBColumns.MASTER_ID)).thenReturn(masterID);
        when(resultSet.getString(DBColumns.MASTER_F_NAME)).thenReturn(masterFirstName);
        when(resultSet.getString(DBColumns.MASTER_L_NAME)).thenReturn(masterLastName);
        when(resultSet.getString(DBColumns.STATUS)).thenReturn(orderStatus);
        when(resultSet.getString(DBColumns.MANAGER_COMMENT)).thenReturn(managerComment);

        LocalDateTime dateTime = LocalDateTime.now();
        when(resultSet.getTimestamp(DBColumns.CREATION_DATE)).thenReturn(Timestamp.valueOf(dateTime));
        when(resultSet.getTimestamp(DBColumns.REPAIR_COMPLETION_DATE)).
                thenReturn(Timestamp.valueOf(dateTime.plus(90, ChronoUnit.MINUTES)));

        List<Order> order = (List<Order>) HANDLER.get(ResultTemplate.ORDERS).handleResultSet(resultSet);

        assertEquals(3, order.size());
    }

    @ParameterizedTest
    @CsvSource(" 5, 33, UserName, Some review content")
    void handleResultSet_forReviewPattern_returnReview(
            int id, int customerID, String firstName, String reviewContent) throws SQLException {

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(DBColumns.ID)).thenReturn(id);
        when(resultSet.getInt(DBColumns.CUSTOMER_ID)).thenReturn(customerID);
        when(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.REVIEW_CONTENT)).thenReturn(reviewContent);
        LocalDateTime dateTime = LocalDateTime.now();
        when(resultSet.getTimestamp(DBColumns.REVIEW_DATE)).thenReturn(Timestamp.valueOf(dateTime));

        Review review = (Review) HANDLER.get(ResultTemplate.REVIEW).handleResultSet(resultSet);
        User customer = review.getCustomer();
        assertAll(
                () -> assertEquals(id, review.getId()),
                () -> assertEquals(customerID, customer.getId()),
                () -> assertEquals(firstName, customer.getFirstName()),
                () -> assertEquals(reviewContent, review.getReviewContent()),
                () -> assertEquals(dateTime, review.getDateTime()));
    }

    @ParameterizedTest
    @CsvSource(" 5, 33, UserName, Some review content")
    void handleResultSet_forReviewsPattern_returnReviews(
            int id, int customerID, String firstName, String reviewContent) throws SQLException {

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(DBColumns.ID)).thenReturn(id);
        when(resultSet.getInt(DBColumns.CUSTOMER_ID)).thenReturn(customerID);
        when(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).thenReturn(firstName);
        when(resultSet.getString(DBColumns.REVIEW_CONTENT)).thenReturn(reviewContent);
        LocalDateTime dateTime = LocalDateTime.now();
        when(resultSet.getTimestamp(DBColumns.REVIEW_DATE)).thenReturn(Timestamp.valueOf(dateTime));

        List<Review> reviews = (List<Review>) HANDLER.get(ResultTemplate.REVIEWS).handleResultSet(resultSet);

        assertEquals(3, reviews.size());
    }

    @Test
    void handleResultSet_forAmountPattern_returnAmount() throws SQLException {

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(DBColumns.AMOUNT)).thenReturn(100);

        int amountFromDB = (Integer) HANDLER.get(ResultTemplate.AMOUNT).handleResultSet(resultSet);

        assertEquals(100, amountFromDB);
    }

    @Test
    void handleResultSet_whenAmountZero_returnZero() throws SQLException {

        when(resultSet.next()).thenReturn(false);

        int amountFromDB = (Integer) HANDLER.get(ResultTemplate.AMOUNT).handleResultSet(resultSet);

        assertEquals(0, amountFromDB);
    }
}
