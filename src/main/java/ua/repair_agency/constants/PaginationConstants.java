package ua.repair_agency.constants;

public enum PaginationConstants {
    ORDERS_FOR_PAGE(5),
    USERS_FOR_PAGE(10),
    REVIEWS_FOR_HOME(4),
    REVIEWS_FOR_REVIEW(10);
    private int amount;

    PaginationConstants(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
