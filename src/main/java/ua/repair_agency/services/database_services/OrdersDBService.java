package ua.repair_agency.services.database_services;

import ua.repair_agency.constants.OrderStatus;
import ua.repair_agency.constants.Queries;
import ua.repair_agency.constants.ResultTemplate;
import ua.repair_agency.exceptions.DataBaseInteractionException;
import ua.repair_agency.models.forms.OrderEditingForm;
import ua.repair_agency.models.forms.OrderForm;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.models.pagination.PageEntities;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.database_services.connection.DBConnectionsPool;
import ua.repair_agency.services.database_services.repository.Repository;
import ua.repair_agency.services.database_services.repository.impl.UniversalRepositoryFactory;
import ua.repair_agency.services.database_services.result_handler.impl.ResultHandlerFactory;
import ua.repair_agency.services.editing.impl.OrderEditor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public final class OrdersDBService {

    private static final Repository DAO_FACTORY = UniversalRepositoryFactory.getDaoFactory();

    public static void addOrder(OrderForm order) {
        LinkedList<Object> orderFields = new LinkedList<>();
        extractOrderFields(order, orderFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_ORDER.getQuery(), orderFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add order to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractOrderFields(OrderForm order, LinkedList<Object> objects) {
        objects.add(order.getUser().getId());
        objects.add(order.getCreationDate());
        objects.add(order.getCarBrand());
        objects.add(order.getCarModel());
        objects.add(order.getCarYear());
        objects.add(order.getRepairType().name());
        objects.add(order.getRepairDescription());
        objects.add(order.getStatus().name());
    }

    public static Order getOrderById(int orderId) {
        try {
            return (Order) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_ORDER_BY_ID.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDER), orderId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get order from database because of: " + exc.getMessage(), exc);
        }
    }

    public static Order getLastOrderForRegUser(int userId) {
        try {
            return (Order) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_LAST_USER_ORDER.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDER), userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get order from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getManagerOrdersByOffsetAmountStatus(int offset, int amount, OrderStatus status) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    status.name(), offset, amount));
            orders.setEntitiesTotalAmount(getManagerOrdersAmountByStatus(status));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getManagerOrdersAmountByStatus(OrderStatus status) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_STATUS.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    status.name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getCustomerOrdersByOffsetAmountTwoStatuses(User user, int offset, int amount, OrderStatus... statuses) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    user.getId(), statuses[0].name(), statuses[1].name(), offset, amount));
            orders.setEntitiesTotalAmount(getCustomerOrdersAmountByTwoStatuses(user, statuses));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database of such statuses because of: " + exc.getMessage(), exc);
        }
    }

    public static int getCustomerOrdersAmountByTwoStatuses(User user, OrderStatus... statuses) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.CUSTOMER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    user.getId(), statuses[0].name(), statuses[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getMasterOrdersByOffsetAmountTwoStatuses(User user, int offset, int amount, OrderStatus... statuses) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    user.getId(), statuses[0].name(), statuses[1].name(), offset, amount));
            orders.setEntitiesTotalAmount(getMasterOrdersAmountByTwoStatuses(user, statuses));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database of such statuses because of: " + exc.getMessage(), exc);
        }
    }

    public static int getMasterOrdersAmountByTwoStatuses(User user, OrderStatus... statuses) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MASTER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    user.getId(), statuses[0].name(), statuses[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getManagerOrdersByOffsetAmountTwoStatuses(int offset, int amount, OrderStatus... statuses) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    statuses[0].name(), statuses[1].name(), offset, amount));
            orders.setEntitiesTotalAmount(getManagerOrdersAmountByTwoStatuses(statuses));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database of such statuses because of: " + exc.getMessage(), exc);
        }
    }

    public static int getManagerOrdersAmountByTwoStatuses(OrderStatus... statuses) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    statuses[0].name(), statuses[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getCustomerOrdersByOffsetAmountTwoExcludeStatuses(User user, int offset, int amount, OrderStatus... status) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    user.getId(), status[0].name(), status[1].name(), offset, amount));
            orders.setEntitiesTotalAmount(getCustomerOrdersAmountByTwoExcludeStatus(user, status));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getCustomerOrdersAmountByTwoExcludeStatus(User user, OrderStatus... status) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.CUSTOMER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    user.getId(), status[0].name(), status[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getMasterOrdersByOffsetAmountTwoExcludeStatuses(User user, int offset, int amount, OrderStatus... status) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    user.getId(), status[0].name(), status[1].name(), offset, amount));
            orders.setEntitiesTotalAmount(getMasterOrdersAmountByTwoExcludeStatus(user, status));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getMasterOrdersAmountByTwoExcludeStatus(User user, OrderStatus... status) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MASTER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    user.getId(), status[0].name(), status[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static PageEntities<Order> getManagerOrdersByAmountOffsetMultipleExcludeStatuses(int offset, int amount, OrderStatus... statuses) {
        try {
            PageEntities<Order> orders = new PageEntities<>();
            switch (statuses.length) {
                case 2:
                    orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            statuses[0].name(), statuses[1].name(), offset, amount));
                    break;
                case 3:
                    orders.setEntities((List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_THREE_EXCLUDE_STATUSES.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            statuses[0].name(), statuses[1].name(), statuses[2].name(), offset, amount));
                    break;
                default:
                    throw new DataBaseInteractionException(
                            "Can't get orders from database of such statuses amount: " + statuses.length);
            }
            orders.setEntitiesTotalAmount(getManagerOrdersAmountByMultipleExcludeStatuses(statuses));
            return orders;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database of such statuses because of: " + exc.getMessage(), exc);
        }
    }

    public static int getManagerOrdersAmountByMultipleExcludeStatuses(OrderStatus... statuses) {
        try {
            switch (statuses.length) {
                case 2:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            statuses[0].name(), statuses[1].name());
                case 3:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_THREE_EXCLUDE_STATUSES.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            statuses[0].name(), statuses[1].name(), statuses[2].name());
                default:
                    throw new DataBaseInteractionException(
                            "Can't get orders amount from database for this statuses amount: " + statuses.length);
            }
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static void editOrderStatus(String orderID, String status) {
        try(Connection connection = DBConnectionsPool.getConnection()) {
            DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_STATUS.getQuery(),
                    status, orderID);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't edit order status because of: ");
        }
    }

    public static void editOrderStatusCompletionDate(String orderID, String status, LocalDateTime date) {
        try (Connection connection = DBConnectionsPool.getConnection()){
            DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_STATUS_COMPLETION_DATE.getQuery(),
                    status, date, orderID);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't edit order status and date because of: ");
        }
    }

    public static void editOrder(OrderEditingForm form, List<OrderEditor.OrderEdits> edits) {
        Connection connection = null;
        try {
            connection = DBConnectionsPool.getConnection();
            connection.setAutoCommit(false);
            for (OrderEditor.OrderEdits edit : edits) {
                switch (edit) {
                    case PRICE:
                        DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_PRICE.getQuery(),
                                form.getPrice(), form.getId());
                        break;
                    case MASTER_ID:
                        DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_MASTER.getQuery(),
                                form.getMasterID(), form.getId());
                        break;
                    case STATUS:
                        DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_STATUS.getQuery(),
                                form.getStatus().name(), form.getId());
                        break;
                    case MANAGER_COMMENT:
                        DAO_FACTORY.update(connection, Queries.UPDATE_ORDER_MANAGER_COMMENT.getQuery(),
                                form.getManagerComment(), form.getId());
                        break;
                    default:
                        throw new SQLException("Can't edit such order data: " + edit);
                }
            }
            connection.commit();
        } catch (Throwable exc) {
            try {
                connection.rollback();
            } catch (SQLException rlb_exc) {
                throw new DataBaseInteractionException(
                        "Can't edit order and can't rollback editing because of: " +
                                exc.getMessage() + rlb_exc.getMessage(), rlb_exc);
            }
            throw new DataBaseInteractionException("Can't edit order because of: " + exc.getMessage(), exc);
        } finally {
            try {
                connection.close();
            } catch (SQLException cl_exc) {
                throw new DataBaseInteractionException("Can't close connection because of: " + cl_exc.getMessage(), cl_exc);
            }
        }
    }

}