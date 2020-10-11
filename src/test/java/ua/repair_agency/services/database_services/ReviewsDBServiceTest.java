package ua.repair_agency.services.database_services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.repair_agency.models.forms.ReviewForm;
import ua.repair_agency.models.pagination.PageEntities;
import ua.repair_agency.models.review.Review;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewsDBServiceTest {


    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @ParameterizedTest
    @CsvSource("2, Some review content")
    void addingReview_addReviewInDatabase(String customerID, String reviewContent){
        ReviewForm form = mock(ReviewForm.class);
        when(form.getCustomerID()).thenReturn(customerID);
        when(form.getReviewContent()).thenReturn(reviewContent);
        when(form.getDateTime()).thenReturn(LocalDateTime.now());
        assertDoesNotThrow(() ->  ReviewsDBService.addReview(form));
    }

    @ParameterizedTest
    @CsvSource("0, 3")
    void getReviewByOffsetAmountAsPageEntities_returnPageEntitiesObjectWithReviewsAndTotalAmount(int offset, int amount){
        PageEntities<Review> entities = ReviewsDBService.getReviewsByOffsetAmountAsPageEntities(offset, amount);
        List<Review> reviewsList = entities.getEntities();
        assertAll(
                () -> assertTrue(reviewsList.size() <= entities.getEntitiesTotalAmount()),
                () -> assertEquals(3, reviewsList.size()));
    }
}