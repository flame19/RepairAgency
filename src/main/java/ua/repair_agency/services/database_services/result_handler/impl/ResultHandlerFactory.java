package ua.repair_agency.services.database_services.result_handler.impl;

import ua.repair_agency.constants.*;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.models.review.Review;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.result_handler.ResultHandler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ResultHandlerFactory {

    public final static Map<ResultTemplate, ResultHandler<?>> HANDLER = new HashMap();

    static {

        HANDLER.put(ResultTemplate.USER, resultSet -> {
            if (resultSet.next()) {
                User user = new User.UserBuilder().
                        setId(resultSet.getInt(DBColumns.ID)).
                        setFirstName(resultSet.getString(DBColumns.FIRST_NAME)).
                        setLastName(resultSet.getString(DBColumns.LAST_NAME)).
                        setEmail(resultSet.getString(DBColumns.EMAIL)).
                        setPassword(resultSet.getInt(DBColumns.PASSWORD)).
                        setLanguage(resultSet.getString(DBColumns.LANGUAGE)).
                        setRole(Role.valueOf(resultSet.getString(DBColumns.ROLE))).build();
                return user;
            } else {
                return null;
            }
        });

        HANDLER.put(ResultTemplate.USERS, resultSet -> {
            List<User> list = new LinkedList<>();
            User user;
            do {
                user = (User) HANDLER.get(ResultTemplate.USER).handleResultSet(resultSet);
            } while (user != null && list.add(user));
            return list;
        });

        HANDLER.put(ResultTemplate.ORDER, resultSet -> {
            if (resultSet.next()) {
                Order order = new Order.OrderBuilder().
                        setId(resultSet.getInt(DBColumns.ID)).
                        setCustomer(new User.UserBuilder().
                                setId(resultSet.getInt(DBColumns.CUSTOMER_ID)).
                                setFirstName(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).
                                setLastName(resultSet.getString(DBColumns.CUSTOMER_L_NAME)).
                                setEmail(resultSet.getString(DBColumns.EMAIL)).
                                build()).
                        setDate(resultSet.getTimestamp(DBColumns.CREATION_DATE).toLocalDateTime()).
                        setCarBrand(resultSet.getString(DBColumns.BRAND)).
                        setCarModel(resultSet.getString(DBColumns.MODEL)).
                        setCarYearManufacture(resultSet.getString(DBColumns.YEAR)).
                        setRepairType(RepairType.valueOf(resultSet.getString(DBColumns.REPAIR_TYPE))).
                        setRepairDescription(resultSet.getString(DBColumns.REPAIR_DESCRIPTION)).
                        setPrice(resultSet.getDouble(DBColumns.PRICE)).
                        setMaster(new User.UserBuilder().
                                setId(resultSet.getInt(DBColumns.MASTER_ID)).
                                setFirstName(resultSet.getString(DBColumns.MASTER_F_NAME)).
                                setLastName(resultSet.getString(DBColumns.MASTER_L_NAME)).
                                build()).
                        setStatus(OrderStatus.valueOf(resultSet.getString(DBColumns.STATUS))).
                        setManagerComment(resultSet.getString(DBColumns.MANAGER_COMMENT)).
                        build();
                Timestamp repairCompletion = resultSet.getTimestamp(DBColumns.REPAIR_COMPLETION_DATE);
                if (repairCompletion != null) {
                    order.setRepairCompletionDate(repairCompletion.toLocalDateTime());
                }
                return order;
            } else {
                return null;
            }
        });

        HANDLER.put(ResultTemplate.ORDERS, resultSet -> {
            List<Order> list = new LinkedList<>();
            Order order;
            do {
                order = (Order) HANDLER.get(ResultTemplate.ORDER).handleResultSet(resultSet);
            } while (order != null && list.add(order));
            return list;
        });

        HANDLER.put(ResultTemplate.REVIEW, resultSet -> {
            if (resultSet.next()) {
                Review review = new Review.ReviewBuilder().
                        setId(resultSet.getInt(DBColumns.ID)).
                        setCustomer(new User.UserBuilder().
                                setId(resultSet.getInt(DBColumns.CUSTOMER_ID)).
                                setFirstName(resultSet.getString(DBColumns.CUSTOMER_F_NAME)).
                                build()).
                        setDateTime(resultSet.getTimestamp(DBColumns.REVIEW_DATE).toLocalDateTime()).
                        setReviewContent(resultSet.getString(DBColumns.REVIEW_CONTENT)).
                        build();
                return review;
            } else {
                return null;
            }
        });

        HANDLER.put(ResultTemplate.REVIEWS, resultSet -> {
            List<Review> list = new LinkedList<>();
            Review review;
            do {
                review = (Review) HANDLER.get(ResultTemplate.REVIEW).handleResultSet(resultSet);
            } while (review != null && list.add(review));
            return list;
        });

        HANDLER.put(ResultTemplate.EMAIL, resultSet -> !resultSet.next());

        HANDLER.put(ResultTemplate.AMOUNT, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt(DBColumns.AMOUNT);
            } else {
                return 0;
            }
        });
    }

    private ResultHandlerFactory() {
    }
}
