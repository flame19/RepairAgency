package ua.repair_agency.services.database_services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.repair_agency.constants.OrderStatus;
import ua.repair_agency.constants.RepairType;
import ua.repair_agency.exceptions.DataBaseInteractionException;
import ua.repair_agency.models.forms.OrderEditingForm;
import ua.repair_agency.models.forms.OrderForm;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.editing.impl.OrderEditor;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrdersDBServiceTest {
    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @ParameterizedTest
    @CsvSource(" 2, Adding transaction car, Add transaction model, 2008," +
            "PAINTING_WORKS, Some repair description, PENDING")
    void addOrderWithSQLStoredProcedureTransaction_correctData_addOrderAndCarCompletely(
            int id, String carBrand, String carModel, String carYear,
            String repairType, String repairDescription, String status) {
        OrderForm form = mock(OrderForm.class);
        User user = new User.UserBuilder().setId(id).build();
        LocalDateTime dateTime = LocalDateTime.now();
        when(form.getUser()).thenReturn(user);
        when(form.getCreationDate()).thenReturn(dateTime);
        when(form.getCarBrand()).thenReturn(carBrand);
        when(form.getCarModel()).thenReturn(carModel);
        when(form.getCarYear()).thenReturn(carYear);
        when(form.getRepairType()).thenReturn(RepairType.valueOf(repairType));
        when(form.getRepairDescription()).thenReturn(repairDescription);
        when(form.getStatus()).thenReturn(OrderStatus.valueOf(status));

        assertDoesNotThrow(() -> OrdersDBService.addOrder(form));
    }

    @ParameterizedTest
    @CsvSource(" 2, Adding transaction car, Add transaction model, 2008," +
            "PAINTING_WORKS, Some repair description, PENDING")
    void addOrderWithSQLStoredProcedureTransaction_nullData_createFailureAndRollbackCarAdding(
            int id, String carBrand, String carModel, String carYear,
            String repairType, String repairDescription, String status) {
        OrderForm form = mock(OrderForm.class);
        User user = new User.UserBuilder().setId(id).build();
        when(form.getUser()).thenReturn(user);
        when(form.getCreationDate()).thenReturn(null);
        when(form.getCarBrand()).thenReturn(carBrand);
        when(form.getCarModel()).thenReturn(carModel);
        when(form.getCarYear()).thenReturn(carYear);
        when(form.getRepairType()).thenReturn(RepairType.valueOf(repairType));
        when(form.getRepairDescription()).thenReturn(repairDescription);
        when(form.getStatus()).thenReturn(OrderStatus.valueOf(status));

        assertThrows(DataBaseInteractionException.class, () -> OrdersDBService.addOrder(form));
    }

    @ParameterizedTest
    @CsvSource(" 1, 14.50, 6, CAR_WAITING, Order editing transaction was successful.")
    void editingOrderTransaction_correctData_editsOrderCompletely(
            int id, String price, String masterID, String status, String managerComment) {
        OrderEditingForm form = mock(OrderEditingForm.class);
        when(form.getId()).thenReturn(id);
        when(form.getPrice()).thenReturn(price);
        when(form.getMasterID()).thenReturn(masterID);
        when(form.getStatus()).thenReturn(OrderStatus.valueOf(status));
        when(form.getManagerComment()).thenReturn(managerComment);
        List<OrderEditor.OrderEdits> edits = new LinkedList<>();
        edits.add(OrderEditor.OrderEdits.PRICE);
        edits.add(OrderEditor.OrderEdits.MASTER_ID);
        edits.add(OrderEditor.OrderEdits.STATUS);
        edits.add(OrderEditor.OrderEdits.MANAGER_COMMENT);

        assertDoesNotThrow(() -> OrdersDBService.editOrder(form, edits));
    }

    @ParameterizedTest
    @CsvSource(" 1, 9999999999, -15, CAR_WAITING")
    void editingUserTransaction_failure_rollbackPreviousUpdates(
            int id, String price, String masterID, String status) {
        OrderEditingForm form = mock(OrderEditingForm.class);
        when(form.getId()).thenReturn(id);
        when(form.getPrice()).thenReturn(price);
        when(form.getMasterID()).thenReturn(masterID);
        when(form.getStatus()).thenReturn(OrderStatus.valueOf(status));
        when(form.getManagerComment()).thenThrow(new DataBaseInteractionException("Some database failure"));
        List<OrderEditor.OrderEdits> edits = new LinkedList<>();
        edits.add(OrderEditor.OrderEdits.PRICE);
        edits.add(OrderEditor.OrderEdits.MASTER_ID);
        edits.add(OrderEditor.OrderEdits.STATUS);
        edits.add(OrderEditor.OrderEdits.MANAGER_COMMENT);

        assertThrows(DataBaseInteractionException.class, () -> OrdersDBService.editOrder(form, edits));
    }

}
