package ua.repair_agency.services.database_services;

import ua.repair_agency.constants.Queries;
import ua.repair_agency.constants.ResultTemplate;
import ua.repair_agency.exceptions.DataBaseInteractionException;
import ua.repair_agency.models.forms.ReviewForm;
import ua.repair_agency.models.pagination.PageEntities;
import ua.repair_agency.models.review.Review;
import ua.repair_agency.services.database_services.connection.DBConnectionsPool;
import ua.repair_agency.services.database_services.repository.Repository;
import ua.repair_agency.services.database_services.repository.impl.UniversalRepositoryFactory;
import ua.repair_agency.services.database_services.result_handler.impl.ResultHandlerFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class ReviewsDBService {
    private static final Repository DAO_FACTORY = UniversalRepositoryFactory.getDaoFactory();

    public static void addReview(ReviewForm reviewForm){
        LinkedList<Object> reviewFields = new LinkedList<>();
        extractReviewFields(reviewForm, reviewFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_REVIEW.getQuery(), reviewFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add review to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractReviewFields(ReviewForm reviewForm, LinkedList<Object> reviewFields) {
        reviewFields.add(reviewForm.getCustomerID());
        reviewFields.add(reviewForm.getDateTime());
        reviewFields.add(reviewForm.getReviewContent());
    }

    public static PageEntities<Review> getReviewsByOffsetAmountAsPageEntities(int offset, int amount) {
        try {
            PageEntities<Review> reviews = new PageEntities<>();
            reviews.setEntities((List<Review>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_REVIEWS_BY_OFFSET_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.REVIEWS), offset, amount));
            reviews.setEntitiesTotalAmount(geReviewsTotalAmount());
            return reviews;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get review from database because of: " + exc.getMessage(), exc);
        }
    }

    private static int geReviewsTotalAmount() {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_REVIEW_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT));
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }
}
