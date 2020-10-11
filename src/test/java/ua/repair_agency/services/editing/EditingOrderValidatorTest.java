package ua.repair_agency.services.editing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.repair_agency.constants.CommonConstants;
import ua.repair_agency.constants.OrderStatus;
import ua.repair_agency.models.forms.OrderEditingForm;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class EditingOrderValidatorTest {

    @Mock
    private OrderEditingForm form;

    private final Set<String> inconsistencies = new HashSet<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        inconsistencies.clear();
    }

    @Test
    void checkingIfNeedMaster_needCase_createMasterInconsistency() {
        when(form.getStatus()).thenReturn(OrderStatus.CAR_WAITING);
        when(form.getMasterID()).thenReturn("0");

        EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);

        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains(CommonConstants.MASTER)));
    }

    @Test
    void checkingIfNeedMaster_notNeedCase_createNoInconsistencies() {
        when(form.getStatus()).thenReturn(OrderStatus.REJECTED);
        when(form.getMasterID()).thenReturn("0");

        EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);

        assertTrue(inconsistencies.isEmpty());
    }

    @Test
    void checkingIfNeedPrice_needCase_createPriceInconsistency() {
        when(form.getStatus()).thenReturn(OrderStatus.CAR_WAITING);
        when(form.getPrice()).thenReturn("0");

        EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);

        assertAll(
                () -> assertEquals(1, inconsistencies.size()),
                () -> assertTrue(inconsistencies.contains(CommonConstants.PRICE)));
    }

    @Test
    void checkingIfNeedPrice_notNeedCase_createNoInconsistencies() {
        when(form.getStatus()).thenReturn(OrderStatus.REJECTED);
        when(form.getPrice()).thenReturn("0");

        EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);

        assertTrue(inconsistencies.isEmpty());
    }
}
