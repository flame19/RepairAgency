package ua.repair_agency.controllers.servlet.commands.impl;

import ua.repair_agency.constants.*;
import ua.repair_agency.controllers.servlet.commands.RequestHandler;
import ua.repair_agency.exceptions.AuthenticationException;
import ua.repair_agency.models.forms.*;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.authentication.UserAuthenticator;
import ua.repair_agency.services.database_services.OrdersDBService;
import ua.repair_agency.services.database_services.ReviewsDBService;
import ua.repair_agency.services.database_services.UsersDBService;
import ua.repair_agency.services.editing.EditingOrderValidator;
import ua.repair_agency.services.editing.impl.OrderEditor;
import ua.repair_agency.services.editing.impl.UserEditor;
import ua.repair_agency.services.validation.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataHandleCommands {

    public static final Map<String, RequestHandler> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put(CRAPaths.LOGIN, (req, resp) -> {
            LoginForm loginForm = new LoginForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(loginForm);
            try {
                if (inconsistencies.isEmpty()) {
                    User user = UserAuthenticator.authenticate(loginForm);
                    addUserToSession(req, user);
                    String targetPath = defineTargetPathAfterLogin(req, user);
                    resp.sendRedirect(req.getContextPath() + targetPath);
                } else {
                    if (inconsistencies.contains(Parameters.EMAIL)) {
                        throw new AuthenticationException(AuthenticationException.Type.EMAIL);
                    } else {
                        throw new AuthenticationException(AuthenticationException.Type.PASS);
                    }
                }
            } catch (AuthenticationException exc) {
                inconsistencies.add(exc.getType().name());
                setDataForBadRequest(req, resp, inconsistencies, loginForm);
                ContentProvideCommands.COMMANDS.get(CRAPaths.LOGIN).handleRequest(req, resp);
                throw new AuthenticationException(exc.getType());
            }
        });

        COMMANDS.put(CRAPaths.REGISTRATION, (req, resp) -> {
            RegistrationForm registrationForm = new RegistrationForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(registrationForm);
            if (inconsistencies.isEmpty()) {
                UsersDBService.createUser(registrationForm);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                setDataForBadRequest(req, resp, inconsistencies, registrationForm);
            }
            ContentProvideCommands.COMMANDS.get(CRAPaths.REGISTRATION).handleRequest(req, resp);
        });

        COMMANDS.put(CRAPaths.MAN_MAS_REGISTRATION, (req, resp) -> {
            RegistrationForm registrationForm = new RegistrationForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(registrationForm);
            if (inconsistencies.isEmpty()) {
                UsersDBService.createUser(registrationForm);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                setDataForBadRequest(req, resp, inconsistencies, registrationForm);
            }
            ContentProvideCommands.COMMANDS.get(CRAPaths.MAN_MAS_REGISTRATION).handleRequest(req, resp);
        });

        COMMANDS.put(CRAPaths.CREATE_ORDER, (req, resp) -> {
            OrderForm orderForm = new OrderForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(orderForm);
            if (inconsistencies.isEmpty()) {
                OrdersDBService.addOrder(orderForm);
                Order order = OrdersDBService.getLastOrderForRegUser(orderForm.getUser().getId());
                req.setAttribute(Attributes.MADE_ORDER, order);
            } else {
                setDataForBadRequest(req, resp, inconsistencies, orderForm);
            }
            ContentProvideCommands.COMMANDS.get(CRAPaths.CREATE_ORDER).handleRequest(req, resp);
        });

        COMMANDS.put(CRAPaths.DELETE_USER, (req, resp) -> {
            int userId = Integer.parseInt(req.getParameter(Parameters.DELETING_USER_ID));
            UsersDBService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + CRAPaths.ADMIN_HOME);
        });

        COMMANDS.put(CRAPaths.EDIT_USER, (req, resp) -> {
            UserEditingForm form = new UserEditingForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            if (inconsistencies.isEmpty()) {
                User user = UsersDBService.getUserByID(form.getId());
                new UserEditor(form, user).
                        compareFirstName().
                        compareLastName().
                        compareEmail().
                        compareRole().edit();
                resp.sendRedirect(req.getContextPath() + CRAPaths.ADMIN_HOME);
            } else {
                setDataForBadRequest(req, resp, inconsistencies, form);
                ContentProvideCommands.COMMANDS.get(CRAPaths.EDIT_USER).handleRequest(req, resp);
            }
        });

        COMMANDS.put(CRAPaths.EDIT_ORDER, (req, resp) -> {
            OrderEditingForm form = new OrderEditingForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);
            EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);
            Order order = OrdersDBService.getOrderById(form.getId());
            if (inconsistencies.isEmpty()) {
                new OrderEditor(form, order).
                        comparePrice().
                        compareMasters().
                        compareStatus().
                        compareManagerComment().edit();
                resp.sendRedirect(req.getContextPath() + CRAPaths.MANAGER_HOME);
            } else {
                List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                req.setAttribute(Attributes.CUR_ORDER_STATUS, order.getStatus());
                req.setAttribute(Attributes.MASTERS, masters);
                setDataForBadRequest(req, resp, inconsistencies, form);
                ContentProvideCommands.COMMANDS.get(CRAPaths.EDIT_ORDER).handleRequest(req, resp);
            }
        });

        COMMANDS.put(CRAPaths.EDIT_STATUS, (req, resp) -> {
            String status = req.getParameter(Parameters.STATUS);
            String orderID = req.getParameter(Parameters.ORDER_ID);
            if (status.equals(OrderStatus.REPAIR_WORK.name())) {
                OrdersDBService.editOrderStatus(orderID, status);
            } else if (status.equals(OrderStatus.REPAIR_COMPLETED.name())) {
                OrdersDBService.editOrderStatusCompletionDate(orderID, status, LocalDateTime.now());
            }
            resp.sendRedirect(req.getContextPath() + CRAPaths.MASTER_HOME);
        });

        COMMANDS.put(CRAPaths.REVIEWS, (req, resp) -> {
            ReviewForm form = new ReviewForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            if (inconsistencies.isEmpty()) {
                ReviewsDBService.addReview(form);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                setDataForBadRequest(req, resp, inconsistencies, form);
            }
            ContentProvideCommands.COMMANDS.get(CRAPaths.REVIEWS).handleRequest(req, resp);
        });
    }

    private DataHandleCommands() {
    }

    private static void setDataForBadRequest(HttpServletRequest req, HttpServletResponse resp, Set<String> inconsistencies, Form form) {
        req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
        req.setAttribute(Attributes.PREV_FORM, form);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static String defineTargetPathAfterLogin(HttpServletRequest req, User user) {
        switch (user.getRole()) {
            case CUSTOMER:
                String path = (String) req.getSession().getAttribute(Attributes.TO_CREATE_ORDER);
                if (path != null) {
                    return path;
                } else {
                    return CRAPaths.CUSTOMER_HOME;
                }
            case ADMIN:
                return CRAPaths.ADMIN_HOME;
            case MANAGER:
                return CRAPaths.MANAGER_HOME;
            case MASTER:
                return CRAPaths.MASTER_HOME;
            default:
                return CRAPaths.HOME;
        }
    }

    private static void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute(Attributes.USER, user);
    }
}
