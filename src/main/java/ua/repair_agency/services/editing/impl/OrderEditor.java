package ua.repair_agency.services.editing.impl;

import ua.repair_agency.models.forms.OrderEditingForm;
import ua.repair_agency.models.order.Order;
import ua.repair_agency.services.database_services.OrdersDBService;
import ua.repair_agency.services.editing.Editor;

import java.util.LinkedList;
import java.util.List;

public class OrderEditor implements Editor {
    private final OrderEditingForm form;
    private final Order order;
    private final List<OrderEdits> edits;

    public OrderEditor(OrderEditingForm form, Order order) {
        this.form = form;
        this.order = order;
        edits = new LinkedList<>();
    }

    public OrderEditor comparePrice(){
        double formPrice = Double.parseDouble(form.getPrice());
        if(formPrice > 0 &&  formPrice != order.getPrice()){
            edits.add(OrderEdits.PRICE);
        }
        return this;
    }

    public OrderEditor compareMasters(){
        int masterID = Integer.parseInt(form.getMasterID());
        if(masterID != 0 && masterID != order.getMaster().getId()){
            edits.add(OrderEdits.MASTER_ID);
        }
        return this;
    }

    public OrderEditor compareStatus(){
        if(!form.getStatus().equals(order.getStatus())){
            edits.add(OrderEdits.STATUS);
        }
        return this;
    }

    public OrderEditor compareManagerComment(){
        if(!form.getManagerComment().equals(order.getManagerComment())){
            edits.add(OrderEdits.MANAGER_COMMENT);
        }
        return this;
    }

    @Override
    public List<OrderEdits> getEdits() {
        return edits;
    }

    @Override
    public void edit() {
        OrdersDBService.editOrder(form, edits);
    }

    public enum OrderEdits{
        PRICE,
        MASTER_ID,
        STATUS,
        MANAGER_COMMENT
    }
}
