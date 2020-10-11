package ua.repair_agency.constants;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(Arrays.asList(CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.LOGOUT, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE)),
    MANAGER(Arrays.asList(CRAPaths.MANAGER_HOME, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE)),
    MASTER(Arrays.asList(CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.LOGOUT,
            CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE)),
    CUSTOMER(Arrays.asList(CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY,
            CRAPaths.LOGOUT,CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE)),
    UNKNOWN(Arrays.asList(CRAPaths.LOGIN, CRAPaths.REGISTRATION, CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.LANGUAGE));

    private List<String> urls;

    Role(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
