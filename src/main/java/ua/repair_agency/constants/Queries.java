package ua.repair_agency.constants;

public enum Queries {

    INSERT_USER("INSERT INTO users(first_name, last_name, email, password, language, role) " +
            "VALUES(?, ?, ?, ?, ?, ?)"),
    INSERT_ORDER("CALL add_order(?, ?, ?, ?, ?, ?, ?, ?)"),

    INSERT_REVIEW("INSERT INTO reviews(customer_id, review_date, review_content) VALUES(?, ?, ?)"),

    SELECT_REVIEWS_BY_OFFSET_AMOUNT("SELECT reviews.id, reviews.review_date, reviews.review_content, " +
            "reviews.customer_id, customer.first_name as customer_f_name " +
            "FROM reviews JOIN users as customer ON reviews.customer_id = customer.id ORDER BY id DESC LIMIT ?, ?"),
    SELECT_REVIEW_AMOUNT("SELECT count(id) as amount FROM reviews"),

    SELECT_USER_BY_ID("SELECT * FROM users WHERE id = ?"),
    SELECT_USER_BY_EMAIL("SELECT * FROM users WHERE email = ?"),
    SELECT_USERS_BY_ROLE("SELECT * FROM users WHERE role = ?"),
    SELECT_USERS_BY_ROLE_OFFSET_AMOUNT("SELECT * FROM users WHERE role = ? LIMIT ?, ?"),
    SELECT_USERS_AMOUNT_BY_ROLE("SELECT count(id) as amount FROM users WHERE role = ?"),

    SELECT_EMAIL("SELECT email FROM users WHERE email = ?"),

    SELECT_ORDER_BY_ID("SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
            "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
            "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
            "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
            "FROM orders " +
            "JOIN cars ON orders.car_id = cars.id " +
            "JOIN users as customer ON orders.customer_id = customer.id " +
            "LEFT JOIN users as master ON orders.master_id = master.id " +
            "WHERE orders.id = ?"),
    SELECT_LAST_USER_ORDER(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.customer_id = ? ORDER BY orders.id DESC LIMIT 1"),
    CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.customer_id = ? AND orders.status IN (?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.customer_id = ? AND orders.status NOT IN (?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    CUSTOMER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE customer_id = ? AND status NOT IN (?, ?)"),
    CUSTOMER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE customer_id = ? AND status IN (?, ?)"),
    MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.master_id = ? AND orders.status IN (?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.master_id = ? AND orders.status NOT IN (?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    MASTER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE master_id = ? AND status IN (?, ?)"),
    MASTER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE master_id = ? AND status NOT IN (?, ?)"),

    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_STATUS(
            "SELECT count(id) as amount FROM orders WHERE status = ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status = ? OR orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_EXCLUDE_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status NOT IN (?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_THREE_EXCLUDE_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.models, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status NOT IN (?, ?, ?) ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE status IN (?, ?)"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_EXCLUDE_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE status NOT IN (?, ?)"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_THREE_EXCLUDE_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE status NOT IN (?, ?, ?)"),

    UPDATE_USER_FIST_NAME("UPDATE users SET first_name = ? WHERE id = ?"),
    UPDATE_USER_LAST_NAME("UPDATE users SET last_name = ? WHERE id = ?"),
    UPDATE_USER_EMAIL("UPDATE users SET email = ? WHERE id = ?"),
    UPDATE_USER_ROLE("UPDATE users SET role = ? WHERE id = ?"),
    UPDATE_USER_LANGUAGE("UPDATE users SET language = ? WHERE id = ?"),

    UPDATE_ORDER_PRICE("UPDATE orders SET price = ? WHERE id = ?"),
    UPDATE_ORDER_MASTER("UPDATE orders SET master_id = ? WHERE id = ?"),
    UPDATE_ORDER_STATUS("UPDATE orders SET status = ? WHERE id = ?"),
    UPDATE_ORDER_MANAGER_COMMENT("UPDATE orders SET manager_comment = ? WHERE id = ?"),
    UPDATE_ORDER_STATUS_COMPLETION_DATE("UPDATE orders SET status = ?, repair_completion_date = ? WHERE id = ?"),

    DELETE_USER_BY_ID("DELETE FROM users WHERE id = ?"),
    DELETE_USER_BY_EMAIL("DELETE FROM users WHERE email = ?")

    ;

    private String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}