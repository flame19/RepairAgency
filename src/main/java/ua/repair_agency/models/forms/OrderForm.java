package ua.repair_agency.models.forms;

import ua.repair_agency.constants.*;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.validation.annotations.MustConform;
import ua.repair_agency.services.validation.annotations.NotEmpty;
import ua.repair_agency.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class OrderForm implements Form {

    private final User user;

    @MustConform(Regex.CAR_BRAND)
    private final String carBrand;

    @MustConform(Regex.CAR_MODEL)
    private final String carModel;

    @MustConform(Regex.CAR_YEAR)
    private final String carYear;

    @NotEmpty
    private final RepairType repairType;

    @NotEmpty
    private final String repairDescription;

    private final LocalDateTime creationDate;

    private final OrderStatus status;

    public OrderForm(HttpServletRequest req) {
        user = extractUser(req);
        carBrand = req.getParameter(Parameters.CAR_BRAND);
        carModel = req.getParameter(Parameters.CAR_MODEL);
        carYear = req.getParameter(Parameters.CAR_YEAR);
        repairDescription = req.getParameter(Parameters.REPAIR_DESCRIPTION);
        repairType = extractRepairType(req);
        creationDate = LocalDateTime.now();
        status = OrderStatus.PENDING;
    }

    private User extractUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute(Attributes.USER);
    }

    private RepairType extractRepairType(HttpServletRequest req) {
        String repairType = req.getParameter(Parameters.REPAIR_TYPE);
        if (repairType != null && !repairType.equals(CommonConstants.REP_TYPE)) {
            return RepairType.valueOf(req.getParameter(Parameters.REPAIR_TYPE));
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
