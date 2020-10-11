package ua.repair_agency.services.editing.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.OrderStatus;
import ua.repair_agency.models.forms.OrderEditingForm;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.models.user.User;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class OrderEditorTest {

    private OrderEditor editor;
    private List<OrderEditor.OrderEdits> edits = new LinkedList<>();

    @Mock
    OrderEditingForm editingForm;

    @Mock
    Order order;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        editor = new OrderEditor(editingForm, order);
        edits.clear();
    }

    @ParameterizedTest
    @CsvSource({"100, 15.4", "65.32, 10"})
    void comparing_differentPrice_giveOnePriceEdit(String formPrice, double orderPrice) {
        when(editingForm.getPrice()).thenReturn(formPrice);
        when(order.getPrice()).thenReturn(orderPrice);
        editor = editor.comparePrice();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.PRICE)));
    }

    @ParameterizedTest
    @CsvSource({"100, 100.0", "65.32, 65.32"})
    void comparing_samePrice_giveNoEdit(String formPrice, double orderPrice) {
        when(editingForm.getPrice()).thenReturn(formPrice);
        when(order.getPrice()).thenReturn(orderPrice);
        editor = editor.comparePrice();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"21, 15", "3, 31"})
    void comparing_differentMasters_giveOneMasterEdit(String formMasterID, int orderMasterID) {
        User user = new User.UserBuilder().setId(orderMasterID).build();
        when(editingForm.getMasterID()).thenReturn(formMasterID);
        when(order.getMaster()).thenReturn(user);
        editor = editor.compareMasters();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.MASTER_ID)));
    }

    @ParameterizedTest
    @CsvSource({"21, 21", "3, 3"})
    void comparing_sameMasters_giveNoEdit(String formMasterID, int orderMasterID) {
        User user = new User.UserBuilder().setId(orderMasterID).build();
        when(editingForm.getMasterID()).thenReturn(formMasterID);
        when(order.getMaster()).thenReturn(user);
        editor = editor.compareMasters();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"REJECTED, CAR_WAITING", "REPAIR_COMPLETED, ORDER_COMPLETED"})
    void comparing_differentStatuses_giveOneStatusEdit(String formStatus, String orderStatus) {
        when(editingForm.getStatus()).thenReturn(OrderStatus.valueOf(formStatus));
        when(order.getStatus()).thenReturn(OrderStatus.valueOf(orderStatus));
        editor = editor.compareStatus();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.STATUS)));
    }

    @ParameterizedTest
    @CsvSource({"CAR_WAITING, CAR_WAITING", "REPAIR_COMPLETED, REPAIR_COMPLETED"})
    void comparing_sameStatuses_giveNoEdit(String formStatus, String orderStatus) {
        when(editingForm.getStatus()).thenReturn(OrderStatus.valueOf(formStatus));
        when(order.getStatus()).thenReturn(OrderStatus.valueOf(orderStatus));
        editor = editor.compareStatus();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"Comment from form, Comment from database",
            "Another comment from form, Another comment form database"})
    void comparing_differentManagerComments_giveOneStatusEdit(String formComment, String orderComment) {
        when(editingForm.getManagerComment()).thenReturn(formComment);
        when(order.getManagerComment()).thenReturn(orderComment);
        editor = editor.compareManagerComment();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(1, edits.size()),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.MANAGER_COMMENT)));
    }

    @ParameterizedTest
    @CsvSource({"Comment is same in the form and database, Comment is same in the form and database",
            "Another comment is same in the from and database, Another comment is same in the from and database"})
    void comparing_sameManagerComments_giveNoEdit(String formComment, String orderComment) {
        when(editingForm.getManagerComment()).thenReturn(formComment);
        when(order.getManagerComment()).thenReturn(orderComment);
        editor = editor.compareManagerComment();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }


    @ParameterizedTest
    @CsvSource({"100.50, 21, REJECTED, Some comment, 15.25, 3, CAR_WAITING, Other comment"})
    void comparing_differentData_giveFourEdits(String formPrice, String formMasterID, String formStatus, String formComment,
                                               double orderPrice, int orderMasterID, String orderStatus, String orderComment) {
        User user = new User.UserBuilder().setId(orderMasterID).build();
        when(editingForm.getPrice()).thenReturn(formPrice);
        when(order.getPrice()).thenReturn(orderPrice);
        when(editingForm.getMasterID()).thenReturn(formMasterID);
        when(order.getMaster()).thenReturn(user);
        when(editingForm.getStatus()).thenReturn(OrderStatus.valueOf(formStatus));
        when(order.getStatus()).thenReturn(OrderStatus.valueOf(orderStatus));
        when(editingForm.getManagerComment()).thenReturn(formComment);
        when(order.getManagerComment()).thenReturn(orderComment);
        editor = editor.comparePrice().compareMasters().compareStatus().compareManagerComment();
        edits = editor.getEdits();
        assertAll(
                () -> assertEquals(4, edits.size()),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.PRICE)),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.MASTER_ID)),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.STATUS)),
                () -> assertTrue(edits.contains(OrderEditor.OrderEdits.MANAGER_COMMENT)));
    }

    @ParameterizedTest
    @CsvSource({"100.50, 21, CAR_WAITING, Some comment, 100.50, 21, CAR_WAITING, Some comment"})
    void comparing_sameData_giveNoEdits(String formPrice, String formMasterID, String formStatus, String formComment,
                                        double orderPrice, int orderMasterID, String orderStatus, String orderComment) {
        User user = new User.UserBuilder().setId(orderMasterID).build();
        when(editingForm.getPrice()).thenReturn(formPrice);
        when(order.getPrice()).thenReturn(orderPrice);
        when(editingForm.getMasterID()).thenReturn(formMasterID);
        when(order.getMaster()).thenReturn(user);
        when(editingForm.getStatus()).thenReturn(OrderStatus.valueOf(formStatus));
        when(order.getStatus()).thenReturn(OrderStatus.valueOf(orderStatus));
        when(editingForm.getManagerComment()).thenReturn(formComment);
        when(order.getManagerComment()).thenReturn(orderComment);
        editor = editor.comparePrice().compareMasters().compareStatus().compareManagerComment();
        edits = editor.getEdits();
        assertTrue(edits.isEmpty());
    }
}
