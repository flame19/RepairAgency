package ua.repair_agency.services.resources;

import ua.repair_agency.constants.CommonConstants;

import java.util.ResourceBundle;

public final class ApplicationResourceBundle {

    public static ResourceBundle resourceBundle = ResourceBundle.getBundle(CommonConstants.APPLICATION);

    public static void setTestBundle(){
        resourceBundle = ResourceBundle.getBundle(CommonConstants.TEST_APPLICATION);
    }

    private ApplicationResourceBundle() {
    }
}
