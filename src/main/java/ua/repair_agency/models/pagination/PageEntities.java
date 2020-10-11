package ua.repair_agency.models.pagination;

import java.util.List;

public class PageEntities<T> {
    private List<T> entities;
    private int entitiesTotalAmount;

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getEntitiesTotalAmount() {
        return entitiesTotalAmount;
    }

    public void setEntitiesTotalAmount(int entitiesTotalAmount) {
        this.entitiesTotalAmount = entitiesTotalAmount;
    }
}
