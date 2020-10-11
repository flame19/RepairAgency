package ua.repair_agency.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class ResourcesWithAsideMenu {
    public static final Set<String> RESOURCES = new HashSet<>();
    static {
        RESOURCES.addAll(Arrays.asList(
                CRAPaths.HOME, CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY,
                CRAPaths.MANAGER_HOME, CRAPaths.EDIT_ORDER, CRAPaths.ACTIVE_ORDERS, CRAPaths.ORDER_HISTORY,
                CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS,
                CRAPaths.ADMIN_HOME, CRAPaths.EDIT_USER, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.REVIEWS));
    }

    private ResourcesWithAsideMenu() {
    }
}
