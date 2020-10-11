package ua.repair_agency.models.order;

import ua.repair_agency.constants.OrderStatus;
import ua.repair_agency.constants.RepairType;
import ua.repair_agency.models.user.User;

import java.time.LocalDateTime;

public class Order {

    private int id;
    private User customer;
    private LocalDateTime date;
    private String carBrand;
    private String carModel;
    private String carYearManufacture;
    private RepairType repairType;
    private String repairDescription;
    private double price;
    private User master;
    private LocalDateTime repairCompletionDate;
    private OrderStatus status;
    private String managerComment;

    private Order(){}

    public int getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarYearManufacture() {
        return carYearManufacture;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public LocalDateTime getRepairCompletionDate() {
        return repairCompletionDate;
    }

    public void setRepairCompletionDate(LocalDateTime repairCompletionDate) {
        this.repairCompletionDate = repairCompletionDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    public static class OrderBuilder{

        private int id;
        private User customer;
        private LocalDateTime date;
        private String carBrand;
        private String carModel;
        private String carYearManufacture;
        private RepairType repairType;
        private String repairDescription;
        private double price;
        private User master;
        private LocalDateTime repairCompletionDate;
        private OrderStatus status;
        private String managerComment;

        public OrderBuilder(){}

        public OrderBuilder setId(int id){
            this.id = id;
            return this;
        }

        public OrderBuilder setCustomer(User user){
            customer = user;
            return this;
        }

        public OrderBuilder setDate(LocalDateTime date){
            this.date = date;
            return this;
        }

        public OrderBuilder setCarBrand(String brand){
            carBrand = brand;
            return this;
        }

        public OrderBuilder setCarModel(String model){
            carModel = model;
            return this;
        }

        public OrderBuilder setCarYearManufacture(String year) {
            carYearManufacture = year;
            return this;
        }

        public OrderBuilder setRepairType(RepairType type){
            repairType = type;
            return this;
        }

        public OrderBuilder setRepairDescription(String repairDescription){
            this.repairDescription = repairDescription;
            return this;
        }

        public OrderBuilder setPrice(double price){
            this.price = price;
            return this;
        }

        public OrderBuilder setMaster(User user){
            master = user;
            return this;
        }

        public OrderBuilder setRepairCompletionDate(LocalDateTime date){
            repairCompletionDate = date;
            return this;
        }

        public OrderBuilder setStatus(OrderStatus status){
            this.status = status;
            return this;
        }

        public OrderBuilder setManagerComment(String comment){
            managerComment = comment;
            return this;
        }

        public Order build(){
            Order order = new Order();
            order.id = id;
            order.customer = customer;
            order.date = date;
            order.carBrand = carBrand;
            order.carModel = carModel;
            order.carYearManufacture = carYearManufacture;
            order.repairType = repairType;
            order.repairDescription = repairDescription;
            order.price = price;
            order.master = master;
            order.repairCompletionDate = repairCompletionDate;
            order.status = status;
            order.managerComment = managerComment;
            return order;
        }
    }
}
