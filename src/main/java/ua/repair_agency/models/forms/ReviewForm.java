package ua.repair_agency.models.forms;

import ua.repair_agency.constants.Parameters;
import ua.repair_agency.services.validation.annotations.NotEmpty;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ReviewForm implements Form{
    private final String customerID;
    @NotEmpty
    private final String reviewContent;
    private final LocalDateTime dateTime;

    public ReviewForm(HttpServletRequest req) {
        customerID = req.getParameter(Parameters.CUSTOMER_ID);
        reviewContent = req.getParameter(Parameters.REVIEW_CONTENT);
        dateTime = LocalDateTime.now();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
