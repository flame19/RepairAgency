package ua.repair_agency.models.review;

import ua.repair_agency.models.user.User;

import java.time.LocalDateTime;

public class Review {

    private int id;
    private User customer;
    private String reviewContent;
    private LocalDateTime dateTime;

    private Review() {
    }

    public int getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static class ReviewBuilder{

        private int id;
        private User customer;
        private String reviewContent;
        private LocalDateTime dateTime;

        public ReviewBuilder() {
        }

        public ReviewBuilder setId(int id){
            this.id = id;
            return this;
        }

        public ReviewBuilder setCustomer(User customer){
            this.customer = customer;
            return this;
        }

        public ReviewBuilder setReviewContent(String reviewContent){
            this.reviewContent = reviewContent;
            return this;
        }

        public ReviewBuilder setDateTime(LocalDateTime dateTime){
            this.dateTime = dateTime;
            return this;
        }

        public Review build(){
            Review review = new Review();
            review.id = id;
            review.customer = customer;
            review.reviewContent = reviewContent;
            review.dateTime = dateTime;
            return review;
        }
    }
}